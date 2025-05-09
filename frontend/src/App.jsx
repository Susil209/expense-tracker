// src/App.jsx
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import ExpenseList from './pages/ExpenseList';
// import Dashboard from './pages/Dashboard';
// import ReportsPage from './pages/ReportsPage';
// import CategoriesPage from './pages/CategoriesPage';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* All of these routes share the same Layout */}
        <Route path="/" element={<Layout />}>
          {/* Index route: renders at “/” */}
          <Route index element={<LandingPage />} />

          {/* Other pages */}
          <Route path="login" element={<LoginPage />} />
          <Route path="expenses" element={<ExpenseList />} />
          {/* <Route path="dashboard" element={<Dashboard />} /> */}
          {/* <Route path="reports" element={<ReportsPage />} /> */}
          {/* <Route path="categories" element={<CategoriesPage />} /> */}

          {/* 404 fallback could go here */}
          {/* <Route path="*" element={<NotFound />} /> */}
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
