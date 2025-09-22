import { Box, Stack, Typography, TextField, Button } from '@mui/material';
import { useState } from 'react';
import type { LoginResponse } from '../dtos/auth/LoginResponse';
import type { LoginRequest } from '../dtos/auth/LoginRequest';
import { sendLoginRequest } from '../utils/auth';
import { useNavigate } from 'react-router-dom';

const saveJwtToken = (response: LoginResponse): void => {
  localStorage.setItem('userId', `${response.userId}`)
  localStorage.setItem('jwtToken', response.accessToken)
  localStorage.setItem('tokenType', response.tokenType)
}

const LoginPage = () => {
  const navigate = useNavigate()
  const [login, setLogin] = useState<string>("")
  const [password, setPassword] = useState<string>("")
  const [wasLoginAttemptFailed, setWasLoginAttemptFailed] = useState<boolean>(false)

  const handleLogin = async () => {
    const body: LoginRequest = {
      login,
      password
    }
    const result = await sendLoginRequest(body)
    if (result.isSuccess) {
      saveJwtToken(result.data)
      setWasLoginAttemptFailed(false)
      navigate(`/users/${result.data.userId}/profile`)
    } else {
      setPassword(() => "")
      setWasLoginAttemptFailed(true)
    }
  }

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh',
      backgroundColor: '#182c44'
     }}>
      <Box sx={{ 
        width: {xs: '90%', lg: '30%'}, 
        height: {xs: '80%', lg: '70%'},
        backgroundColor: 'white', color: 'white', textAlign: 'center',
        paddingY: {xs: 15, sm: 20, md: 30, lg: 0, xl: 6},
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
                value={login}
                onChange={e => setLogin(e.target.value)}
                id='login-input'
                label='Login'
                size='medium'
                autoComplete='off'
                sx={{
                  width: {xs: '75%', md: '60%', lg: '65%'}, 
                  mb: {xs: 5, sm: 4}}}/>
              <TextField
                value={password}
                onChange={e => setPassword(e.target.value)}
                id='password-input'
                type='password'
                label='Password'
                size='medium'
                autoComplete='off'
                sx={{
                  width: {xs: '75%', md: '60%', lg: '65%'}}}/>
            </Box>
            <Box>
              <Button variant='contained' size='large' onClick={handleLogin}>
                Sign In
              </Button>
              <Button variant='outlined' size='large' onClick={() => navigate('/register')}>
                Sign Up
              </Button>
            </Box>
            { wasLoginAttemptFailed ? 
              <Box>
                <Typography sx={{color: 'red', fontWeight: '600'}}>
                  Invalid credentials
                </Typography>
              </Box> :
              <></>
            }
          </Stack>
      </Box>
    </Box>
  );
};

export default LoginPage;
