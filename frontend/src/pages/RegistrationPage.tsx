import { Grid, Box, Container, Typography, TextField, 
  FormControlLabel, Checkbox, Link, Button } from '@mui/material'
import { useTheme, useMediaQuery } from '@mui/material';
import Tooltip from '@mui/material/Tooltip';
import IconButton from '@mui/material/IconButton';
import InfoIcon from '@mui/icons-material/Info';
import goExchangeEasierCaptionImage from '../assets/registration_page/caption.png'
import earthImage from '../assets/registration_page/earth.png';
import registrationPageTextImage from "../assets/registration_page/text.png"

type InputFieldProps = {
  id: string;
  label: string;
  infoText: string;
  isRequired: boolean;
}

const InputField = (props: InputFieldProps) => {
  return (
    <Box sx={{display: 'flex', width: '100%', justifyContent: 'center'}}>
      <TextField
        id={props.id}
        label={props.label}
        required={props.isRequired}
        placeholder={`Enter your ${props.label.toLocaleLowerCase()}`}
        autoComplete='off'
        sx={{
          width: {xs: '80%', sm: '60%', md: '50%'},
          paddingRight: {lg: 1}
        }}/>
      <Tooltip title={props.infoText}>
        <IconButton>
          <InfoIcon />
        </IconButton>
      </Tooltip>
    </Box>
  )
}

export const RegistrationPage = () => {
  const theme = useTheme();
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'));

  return (
    <Grid container spacing={2}>
      <Grid size={{xs: 12, lg: 7}}>
        <Container
          sx={{
            backgroundColor: '#182c44',
            height: { xs: '10vh', lg: '100vh' },
            display: 'flex',
            flexDirection: 'column',
            paddingTop: 2,
          }}>
            {isLgScreen ? (
              <>
                <img
                  src={goExchangeEasierCaptionImage}
                  alt="Go Exchange Easier Caption"
                  style={{height: '20%', objectFit: 'contain'}}
                />
                <img
                  src={earthImage}
                  alt='Earth'
                  style={{height: '50%', objectFit: 'contain', marginBottom: 20}}
                />
                <img
                  src={registrationPageTextImage}
                  alt='Find people that already were in places you want to go
                    and pick the best one for you'
                  style={{height: '20%', objectFit: 'contain'}}
                />
              </>  
            ) : (
              <>
                <img
                  src={goExchangeEasierCaptionImage}
                  alt="Go Exchange Easier Caption"
                  style={{ height: '90%', objectFit: 'contain', paddingBottom: 3, 
                    transform: 'rotate(3deg)'}}
                />            
              </>
            )}
        </Container>
      </Grid>
      <Grid size={{xs: 12, lg: 5}}>
        <Container sx={{backgroundColor: 'white', height: '100vh',
          paddingY: {lg: 3}
        }}>
          <Box sx={{display: 'flex', justifyContent: 'center'}}>
            <Typography sx={{
              fontSize: {xs: '1.5rem', sm: '2.3rem', xl: '2rem'},
              paddingTop: {xs: 2, sm: 2, xl: 3}}}>
                REGISTRATION FORM
            </Typography>
          </Box>
          <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', 
            justifyContent: 'space-around', 
            height: {xs: '55%', lg: '60%'},
            paddingTop: {xs: 3, lg: 4}}}>
              <InputField id='login' label='Login' isRequired={true}
                infoText='Should have between 6 to 20 characters. Only letters and numbers.'/>
              <InputField id='password' label='Password' isRequired={true}
                infoText='Should have between 8 to 20 characters. Only letters and numbers.'/>
              <InputField id='nick' label='Nick' isRequired={true}
                infoText='Should have between 6 to 20 characters. Only letters and numbers. 
                Your nick will be shown on your profile. If not provided your login will be used.'/>
              <InputField id='mail' label='Mail' isRequired={false}
                infoText='Should be a valid email. By giving email you let us notify 
                you about new message in the service.'/>
          </Box>
          <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', 
            paddingTop: {lg: 2}}}>
            <FormControlLabel
              control={<Checkbox />}
              label={
                <Box sx={{
                  fontSize: {xs: '0.7rem', sm: '1.2rem', md: '1.3rem', lg: '0.8rem'}}}>
                  <span>
                    I read and agree to {' '}
                  </span>            
                  <Link
                    href='/terms-and-conditions'
                    target='_blank'
                    rel='noopener'
                    sx={{fontWeight: 'bold'}}
                    onClick={() => {}}>
                    Terms and Conditions
                  </Link>
                </Box>
              }/>
            <Button variant="contained" sx={{
              marginTop: {xs: 1.5, lg: 2}
            }}>
              Sign Up
            </Button>
          </Box>
        </Container>
      </Grid>
    </Grid>
  )
}

export default RegistrationPage