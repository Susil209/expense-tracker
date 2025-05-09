// src/pages/LandingPage.jsx
import React from 'react';
import {
  Container,
  Typography,
  Button,
  Box,
  Grid,
  Card,
  CardContent,
} from '@mui/material';
import { grey } from '@mui/material/colors';
import { Link as RouterLink } from 'react-router-dom';
import AddIcon from '@mui/icons-material/Add';
import BarChartIcon from '@mui/icons-material/BarChart';
import CategoryIcon from '@mui/icons-material/Category';

const features = [
  {
    title: 'Add Expenses',
    description: 'Quickly add your expenses and keep track of your spending.',
    icon: <AddIcon fontSize="large" color="primary" />,
  },
  {
    title: 'View Reports',
    description: 'Generate insightful reports to understand your financial habits.',
    icon: <BarChartIcon fontSize="large" color="primary" />,
  },
  {
    title: 'Manage Categories',
    description: 'Organize your expenses into categories for better tracking.',
    icon: <CategoryIcon fontSize="large" color="primary" />,
  },
];

const LandingPage = () => (
  <Box sx={{ backgroundColor: grey[100], minHeight: '100vh' }}>
    <Box sx={{ px: { xs: 2, sm: 4, md: 8 }, pt: 6, pb: 8 }}>
      {/* Hero */}
      <Box textAlign="center" py={8}>
        <Typography variant="h2" component="h1" gutterBottom>
          Track Your Expenses Effortlessly
        </Typography>
        <Typography variant="h6" color="text.secondary" paragraph>
          Manage your finances with ease and confidence.
        </Typography>
        <Box sx={{ mt: 4 }}>
          <Button
            component={RouterLink}
            to="/login"
            variant="contained"
            size="large"
            sx={{ mr: 2 }}
          >
            Get Started
          </Button>
          <Button component={RouterLink} to="/learn-more" variant="text" size="large">
            Learn More
          </Button>
        </Box>
      </Box>

      {/* Features */}
      <Grid container spacing={4} sx={{ mt: 6 }} justifyContent="center">
        {features.map(({ title, description, icon }) => (
          <Grid item xs={12} sm={4} key={title} >
            <Card elevation={1} sx={{ textAlign: 'center', py: 4 }}>
              <CardContent>
                <Box mb={2}>{icon}</Box>
                <Typography variant="h5" gutterBottom>
                  {title}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {description}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  </Box>
);

export default LandingPage;
