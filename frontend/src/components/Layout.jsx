// src/components/Layout.jsx
import React, { useState } from 'react';
import Navbar from './Navbar';
import Sidebar from './Sidebar';
import Footer from './Footer';
import { Box } from '@mui/material';
import { Outlet } from 'react-router-dom';

export default function Layout() {
  const [drawerOpen, setDrawerOpen] = useState(false);
  const toggleDrawer = () => setDrawerOpen(o => !o);

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      {/* Top bar + sidebar */}
      <Navbar onMenuClick={toggleDrawer} />
      <Sidebar open={drawerOpen} onClose={toggleDrawer} onMenuClick={toggleDrawer}/>

      {/* Main content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          backgroundColor: '#f8fafc',
          pt: 2,
          display: 'flex',
        }}
      >
        {/* This is where the active route will be rendered */}
        <Outlet />
      </Box>

      {/* Footer always at bottom */}
      <Footer />
    </Box>
  );
}
