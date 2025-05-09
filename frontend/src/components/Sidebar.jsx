import React from 'react';
import { Drawer, List, ListItemButton, ListItemIcon, ListItemText, Box,IconButton, Typography } from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import ReceiptIcon from '@mui/icons-material/Receipt';
import BarChartIcon from '@mui/icons-material/BarChart';
import CategoryIcon from '@mui/icons-material/Category';
import { Link as RouterLink, useLocation } from 'react-router-dom';
import MenuIcon from '@mui/icons-material/Menu';

const navItems = [
  { label: 'Dashboard', icon: <DashboardIcon />, to: '/dashboard' },
  { label: 'Expenses',  icon: <ReceiptIcon />,   to: '/expenses'  },
  { label: 'Reports',   icon: <BarChartIcon />,  to: '/reports'   },
  { label: 'Categories',icon: <CategoryIcon />,  to: '/categories'},
];

export default function Sidebar({ open, onClose , onMenuClick }) {
  const { pathname } = useLocation();

  return (
    <Drawer
      open={open}
      onClose={onClose}
      variant="temporary"
      ModalProps={{ keepMounted: true }}
      PaperProps={{
        sx: {
          width: 240,
          backgroundColor: '#1e293b',    // slate-800
          color: '#fff',
        }
      }}
    >
      <Box sx={{ mt: 2 }}>
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <IconButton edge="start" onClick={onMenuClick} sx={{ ml: 1 }} color='inherit'>
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
        <List>
          {navItems.map(({ label, icon, to }) => (
            <ListItemButton
              key={label}
              component={RouterLink}
              to={to}
              selected={pathname === to}
              sx={{
                '&.Mui-selected': {
                  backgroundColor: '#3b82f6',  // blue-500
                  '& .MuiListItemIcon-root, & .MuiListItemText-primary': {
                    color: '#fff'
                  }
                }
              }}
              onClick={onClose}
            >
              <ListItemIcon sx={{ color: pathname === to ? '#fff' : '#94a3b8' /* slate-400 */ }}>
                {icon}
              </ListItemIcon>
              <ListItemText primary={label} />
            </ListItemButton>
          ))}
        </List>
      </Box>
    </Drawer>
  );
}
