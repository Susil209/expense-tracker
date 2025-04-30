import { Button, Container, Typography } from '@mui/material'
import './App.css'

function App() {

  return (
    <Container>
      <Typography variant="h1" gutterBottom>
        Expense Tracker
      </Typography>
      <Button variant="contained" color="primary">
        Get Started
      </Button>
    </Container>
  )
}

export default App
