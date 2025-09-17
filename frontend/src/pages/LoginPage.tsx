import { Box, Stack, Typography, TextField, Button } from '@mui/material';

const LoginPage = () => {

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh',
      backgroundColor: '#182c44'
     }}>
      <Box sx={{ 
        width: {xs: '90%', lg: '30%'}, 
        height: {xs: '80%', lg: '70%'},
        backgroundColor: 'white', color: 'white', textAlign: 'center',
        paddingY: {xs: 15, sm: 20, md: 30, lg: 6},
        borderRadius: 3}}>
          <Stack direction='column'
            sx={{ justifyContent: 'space-around', height: '100%' }}>
            <Box>
              <Typography sx={{color: 'black',     
                fontSize: {xs: '1.8rem', sm: '3rem', md: '3rem', lg: '1.8rem'}
                }}>
                WELCOME BACK
              </Typography>
              <Typography sx={{color: 'gray', 
                fontSize: {xs: '1rem', sm: '1.7rem', md: '1.5rem', lg: '0.9rem'}
                }}>
                We are happy to see you again
              </Typography>
            </Box>
            <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
              <TextField
                id='login-text-field'
                label='Login'
                size='medium'
                sx={{
                  width: {xs: '75%', md: '60%', lg: '65%'}, 
                  mb: {xs: 5, sm: 4}}}/>
              <TextField
                id='password-text-field'
                label='Password'
                size='medium'
                sx={{
                  width: {xs: '75%', md: '60%', lg: '65%'}}}/>
            </Box>
            <Box>
              <Button variant='contained' size='large'>
                Sign In
              </Button>
              <Button variant='outlined' size='large'>
                Sign Up
              </Button>
            </Box>
          </Stack>
      </Box>
    </Box>
  );
};

export default LoginPage;



