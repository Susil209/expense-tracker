// src/pages/LoginPage.jsx
import React from 'react';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as Yup from 'yup';
import {
  Box,
  Button,
  Card,
  CardContent,
  TextField,
  Typography
} from '@mui/material';
import { grey } from '@mui/material/colors';
import { useNavigate } from 'react-router-dom';

const schema = Yup.object().shape({
  username: Yup.string().required('Username is required'),
  password: Yup.string().required('Password is required'),
});

export default function LoginPage() {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting }
  } = useForm({ resolver: yupResolver(schema) });

  const onSubmit = async data => {
    try {
        // Perform login via POST /login (form-encoded)
        const formData = new URLSearchParams();
        formData.append('username', data.username);
        formData.append('password', data.password);
  
        const resp = await fetch('/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: formData,
          credentials: 'include' // so the session cookie is stored
        });
  
        if (resp.ok) {
          // Redirect to dashboard on success
          navigate('/dashboard');
        } else {
          // Show error (401 etc)
          alert('Invalid username or password');
        }
      } catch (err) {
        console.error(err);
        alert('Network error');
      }
  };

  return (
    <Box
      component="main"
      sx={{
        position: 'relative',
        width: '100vw',            // span full viewport width
        height: '100vh',           // span full viewport height
        backgroundColor: grey[100],
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        p: 2
      }}
    >
      <Card
        elevation={4}
        sx={{
          maxWidth: 400,
          width: '100%',
          borderRadius: 2
        }}
      >
        <CardContent sx={{ p: 4 }}>
          <Typography variant="h5" align="center" gutterBottom>
            Sign In
          </Typography>

          <Box component="form" noValidate onSubmit={handleSubmit(onSubmit)}>
            <TextField
              label="Username"
              fullWidth
              margin="normal"
              {...register('username')}
              error={!!errors.username}
              helperText={errors.username?.message}
            />

            <TextField
              label="Password"
              type="password"
              fullWidth
              margin="normal"
              {...register('password')}
              error={!!errors.password}
              helperText={errors.password?.message}
            />

            <Box mt={3} display="flex" justifyContent="space-between" alignItems="center">
              <Button variant="text" onClick={() => navigate('/forgot-password')}>
                Forgot password?
              </Button>
              <Button type="submit" variant="contained" disabled={isSubmitting}>
                {isSubmitting ? 'Signing In...' : 'Login'}
              </Button>
            </Box>
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
}
