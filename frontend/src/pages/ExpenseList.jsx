import React from 'react';
import {
  Box,
  Button,
  TextField,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Stack
} from '@mui/material';
import { Add as AddIcon, Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { grey } from '@mui/material/colors';
import { Link as RouterLink } from 'react-router-dom';

const sampleData = [
  { date: '01/01/2023', description: 'Groceries', category: 'Food', amount: '$50' },
  { date: '01/02/2023', description: 'Utilities', category: 'Bills', amount: '$100' },
  { date: '01/03/2023', description: 'Transport', category: 'Travel', amount: '$20' },
  { date: '01/04/2023', description: 'Dining Out', category: 'Food', amount: '$30' },
];

const ExpensesList = () => {
  return (
    <Box sx={{ display: 'flex', width: '100vw', height: '100vh' }}>
      {/* Sidebar is assumed to be in your Layout wrapper */}

      {/* Main content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          backgroundColor: grey[50],
          p: { xs: 2, sm: 4, md: 6 },
          overflow: 'auto'
        }}
      >
        {/* Header + New Expense */}
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            mb: 4
          }}
        >
          <Typography variant="h5" component="h2">
            Expenses
          </Typography>
          <Button
            component={RouterLink}
            to="/expenses/new"
            variant="contained"
            startIcon={<AddIcon />}
          >
            New Expense
          </Button>
        </Box>

        {/* Filters */}
        <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} mb={3}>
          <TextField
            label="Search description..."
            variant="outlined"
            size="small"
          />
          <TextField
            label="Category"
            variant="outlined"
            size="small"
          />
          <TextField
            label="Start Date"
            type="date"
            variant="outlined"
            size="small"
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            label="End Date"
            type="date"
            variant="outlined"
            size="small"
            InputLabelProps={{ shrink: true }}
          />
        </Stack>

        {/* Table */}
        <TableContainer component={Paper} elevation={1}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Date</TableCell>
                <TableCell>Description</TableCell>
                <TableCell>Category</TableCell>
                <TableCell align="right">Amount</TableCell>
                <TableCell align="center">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {sampleData.map((row, idx) => (
                <TableRow key={idx}>
                  <TableCell>{row.date}</TableCell>
                  <TableCell>{row.description}</TableCell>
                  <TableCell>{row.category}</TableCell>
                  <TableCell align="right">{row.amount}</TableCell>
                  <TableCell align="center">
                    <IconButton size="small" color="primary">
                      <EditIcon />
                    </IconButton>
                    <IconButton size="small" color="error">
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>

        {/* Pagination */}
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'flex-end',
            alignItems: 'center',
            mt: 2
          }}
        >
          <Button variant="outlined" size="small" sx={{ mr: 1 }}>
            Previous
          </Button>
          <Button variant="outlined" size="small">
            Next
          </Button>
        </Box>
      </Box>
    </Box>
  );
};

export default ExpensesList;
