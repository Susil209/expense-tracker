import React from 'react';
import {
  AppBar,
  Toolbar,
  IconButton,
  Typography,
  Box,
  Button,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { Link as RouterLink } from 'react-router-dom';

export default function Navbar({ onMenuClick }) {
  return (
    <AppBar position="sticky" color="default" elevation={4}>
      <Toolbar sx={{ justifyContent: 'space-between', px: { xs: 2, sm: 4, md: 8 } }}>
        {/* Left section: Hamburger + Brand */}
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <IconButton edge="start" onClick={onMenuClick} sx={{ mr: 1 }}>
            <MenuIcon />
          </IconButton>
          <Typography
            variant="h6"
            component={RouterLink}
            to="/"
            sx={{
              textDecoration: 'none',
              color: 'inherit',
              fontWeight: 'bold',
            }}
          >
            Expense Tracker
          </Typography>
        </Box>

        {/* Right section: Menu Items */}
        <Box sx={{ display: 'flex', gap: 2 }}>
          <Button component={RouterLink} to="/features" color="inherit">
            Features
          </Button>
          <Button component={RouterLink} to="/pricing" color="inherit">
            Pricing
          </Button>
          <Button
            component={RouterLink}
            to="/login"
            variant="outlined"
            sx={{ borderRadius: 2 }}
          >
            Login
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}
