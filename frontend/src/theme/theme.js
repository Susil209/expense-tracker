import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: { main: '#1976d2' },
    secondary: { main: '#f50057' },
  },
  typography: {
    h1: { fontSize: '2rem', fontWeight: 700 },
    body1: { fontSize: '1rem' },
  },
});

export default theme;
