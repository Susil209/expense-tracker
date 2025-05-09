// src/components/Footer.jsx
import React from 'react';
import { Box, Container, Typography, Link } from '@mui/material';
import { grey } from '@mui/material/colors';
import { Link as RouterLink } from 'react-router-dom';

const Footer = () => (
  <Box sx={{ backgroundColor: grey[200], py: 4, mt: 'auto' }}>
    <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', px: { xs: 2, sm: 4, md: 8 } }}>
      <Typography variant="body2" color="text.secondary">
        © {new Date().getFullYear()} Expense Tracker
      </Typography>
      <Box>
        <Link component={RouterLink} to="/about" variant="body2" sx={{ mr: 2 }}>
          About
        </Link>
        <Link component={RouterLink} to="/contact" variant="body2" sx={{ mr: 2 }}>
          Contact
        </Link>
        <Link component={RouterLink} to="/privacy" variant="body2">
          Privacy
        </Link>
      </Box>
    </Box>
  </Box>
);

export default Footer;
