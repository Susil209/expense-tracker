package com.expense.tracker.controller;

import com.expense.tracker.model.Category;
import com.expense.tracker.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev") // ensure DataLoader runs
public class ExpenseControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long categoryId;


    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        Category cat = new Category();
        cat.setName("IntegrationTestCat");
        categoryId = categoryRepository.save(cat).getId();
    }

    @Test
    void createExpense_andFetchIt() throws Exception {
        // Prepare request payload
        var payload = """
            {
              "amount": 123.45,
              "description": "IT Groceries",
              "date": "%s",
              "categoryId": %d
            }
            """.formatted(LocalDate.now(), categoryId);

        // POST → create
        mockMvc.perform(post("/api/expenses")
                        .with(httpBasic("admin","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/expenses/")))
                .andExpect(jsonPath("$.message", is("Expense created successfully")))
                .andExpect(jsonPath("$.data.id", notNullValue()))
                .andExpect(jsonPath("$.data.amount", is(123.45)));

        // GET → list all, should contain at least one
        mockMvc.perform(get("/api/expenses")
                        .with(httpBasic("admin","password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.message", is("Expenses retrieved successfully")));
    }

    @Test
    void updateAndDeleteExpense() throws Exception {
        // First, create one expense directly via POST
        var createPayload = """
            {
              "amount": 50.00,
              "description": "To Be Updated",
              "date": "%s",
              "categoryId": %d
            }
            """.formatted(LocalDate.now(), categoryId);

        var mvcResult = mockMvc.perform(post("/api/expenses")
                        .with(httpBasic("admin","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated())
                .andReturn();

        // Extract the new ID from Location header
        String location = mvcResult.getResponse().getHeader("Location");
        Long newId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));

        // Prepare update payload
        var updatePayload = """
            {
              "amount": 75.00,
              "description": "Updated via IT",
              "date": "%s",
              "categoryId": %d
            }
            """.formatted(LocalDate.now(), categoryId);

        // PUT → update
        mockMvc.perform(put("/api/expenses/{id}", newId)
                        .with(httpBasic("admin","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Expense updated successfully")))
                .andExpect(jsonPath("$.data.id", is(newId.intValue())))
                .andExpect(jsonPath("$.data.amount", is(75.00)));

        // DELETE → remove
        mockMvc.perform(delete("/api/expenses/{id}", newId)
                        .with(httpBasic("admin","password")))
                .andExpect(status().isNoContent());
    }
}
