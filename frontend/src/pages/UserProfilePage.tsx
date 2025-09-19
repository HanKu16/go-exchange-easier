import { Grid, Box, Avatar, Typography, Container } from '@mui/material'
import basicAvatar from '../assets/examples/basic-avatar.png'
import exampleFlag from '../assets/examples/example-flag.png'
import SchoolIcon from '@mui/icons-material/School';
import PublicIcon from '@mui/icons-material/Public';
import Navbar from '../components/Navbar';
import { useTheme, useMediaQuery } from '@mui/material';
import Button from '@mui/material/Button';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
// import PersonRemoveIcon from '@mui/icons-material/PersonRemove';
import SendIcon from '@mui/icons-material/Send';
import Stack from '@mui/material/Stack';

const ActionButtons = () => {
  return (
    <Stack direction="row" spacing={2} sx={{marginTop: 3}}>
      <Button variant="outlined" endIcon={<PersonAddIcon/>}>
        FOLLOW
      </Button>
      <Button variant="contained" endIcon={<SendIcon/>}>
        SEND MESSAGE
      </Button>
    </Stack>
  );
}

const UserDataPanel = () => {
  const theme = useTheme();
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'));

  return (
    <>
      {isLgScreen ? (
        <Box sx={{ backgroundColor: '#182c44', minHeight: '100vh', display: 'flex', 
          flexDirection: 'column', alignItems: 'center', paddingY: 4}}>
          <Avatar alt="User avatar" src={basicAvatar} sx={{
            width: '20vh', 
            height: '20vh'}}/>
          <Typography sx={{color: 'white', fontSize: {lg: '2rem'}, fontWeight: '700', 
            paddingTop: {lg: 2}}}>
            Jacob Nomada
          </Typography>
          <Box sx={{width: '100%', paddingLeft: {lg: 6}, paddingTop: {lg: 2}}}>
            <Box sx={{display: 'flex'}}>
              <PublicIcon sx={{color: 'white'}}/>
              <Typography sx={{color: 'white', fontSize: {lg: '1.2rem'}, fontWeight: '600',
                paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}, marginLeft: 1}}>
                Canada
              </Typography>
              <img src={exampleFlag} alt="" style={{height: '1rem', marginTop: 4}}/>
            </Box>
            <Box sx={{display: 'flex'}}>
              <SchoolIcon sx={{color: 'white'}}/>
              <Typography sx={{color: 'white', fontSize: {lg: '1rem'}, fontWeight: '500',
                marginLeft: 1}}>
                University of Bologna
              </Typography>
            </Box>
          </Box>
          <ActionButtons/>
          <Box sx={{width: '100%', paddingX: {lg: 5}, paddingTop: {lg: 4}}}>
            <Typography sx={{color: 'white', fontSize: {lg: '1.2rem'}, fontWeight: '600',
                paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}}}>
              About
            </Typography>
            <Typography sx={{color: 'white', fontSize: {lg: '0.8rem'}, fontWeight: '400',
              paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}}}>
              Lorem ipsum dolor sit amet consectetur, adipisicing elit. Distinctio beatae dolor voluptatum magni nemo accusamus iure doloremque itaque ea non, totam illum error vitae sunt, dolore ex commodi maiores animi.
              Facilis quia deleniti illo et voluptatum fugit laudantium illum magni blanditiis pariatur. Blanditiis modi facilis voluptates deserunt nam reiciendis nihil cum cupiditate, debitis ullam necessitatibus est, qui, et impedit ea.
              Aperiam facilis, iste commodi repudiandae reprehenderit delectus quidem, nostrum ducimus ab dolorem dicta magnam incidunt voluptatem rem veritatis, perspiciatis mollitia architecto! Quis, a officia est magni quas non magnam eligendi.
              Consequuntur tempora praesentium ab fugiat cum obcaecati nihil dolorum numquam! Minima eaque cum beatae! Eveniet distinctio sed velit perspiciatis necessitatibus quos nemo, adipisci saepe quisquam mollitia eos repudiandae, dicta quae?
              Unde magnam vel facilis at quae officia iste aut autem accusamus fugiat magni, deleniti minus similique consequuntur nobis debitis, molestiae voluptates nesciunt repellat totam inventore officiis? Inventore et magni commodi?
              Iure aliquid dolores voluptate explicabo? Cum ipsam excepturi molestiae sint eius facere assumenda aliquam eum aliquid vel dolorum nesciunt, at vero, in beatae natus quaerat, veniam maiores quis aperiam a?
              Quasi corrupti dolore recusandae magnam, dignissimos quia, minus voluptas porro saepe suscipit natus nam nihil iure! Ipsa ea, repellat inventore aspernatur eveniet eligendi rem libero, dolore vero eaque laudantium distinctio?
              Repudiandae nesciunt odit earum iste similique praesentium ipsum officiis hic doloremque reiciendis ex quia totam commodi unde, ullam neque sunt maiores asperiores, impedit illo! Nostrum veniam rem placeat architecto! Nihil!
            </Typography>
          </Box>
        </Box> 
      ) : (
      <Box sx={{ backgroundColor: '#182c44', display: 'flex', 
          flexDirection: 'column', paddingY: 2}}>
          <Box sx={{display: 'flex', flexDirection: 'row', paddingLeft: 3}}>
            <Avatar alt="User avatar" src={basicAvatar} sx={{
              width: '10vh', 
              height: '10vh',
              marginRight: 3,
              marginTop: 1}}/>
            <Box sx={{display: 'flex', flexDirection: 'column'}}>
              <Typography sx={{color: 'white', fontSize: '1rem', fontWeight: '700', 
                paddingY: 0.4}}>
                Jacob Nomada
              </Typography>
              <Box sx={{display: 'flex'}}>
                <PublicIcon sx={{color: 'white'}}/>
                <Typography sx={{color: 'white', fontSize: {lg: '0.7rem'}, fontWeight: '600',
                  paddingBottom: 0.5, marginLeft: 1, marginRight: 1}}>
                  Canada
                </Typography>
                <img src={exampleFlag} alt="" style={{height: '0.9rem', marginTop: 4}}/>
              </Box>
              <Box sx={{display: 'flex'}}>
                <SchoolIcon sx={{color: 'white'}}/>
                <Typography sx={{color: 'white', fontSize: {lg: '1rem'}, fontWeight: '500',
                  marginLeft: 1}}>
                  University of Bologna
                </Typography>
              </Box>
            </Box>
          </Box>
          <Container sx={{marginY: 0.5}}>
            <ActionButtons/>
          </Container>
          <Box sx={{width: '100%', paddingX: 3, paddingTop: 4}}>
            <Typography sx={{color: 'white', fontSize: {lg: '1.2rem'}, fontWeight: '600',
                paddingBottom: 1}}>
              About
            </Typography>
            <Typography sx={{color: 'white', fontSize: {lg: '0.8rem'}, fontWeight: '400',
              paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}}}>
              Lorem ipsum dolor sit amet consectetur, adipisicing elit. Distinctio beatae dolor voluptatum magni nemo accusamus iure doloremque itaque ea non, totam illum error vitae sunt, dolore ex commodi maiores animi.
            </Typography>
          </Box>
        </Box> 
      )}
    </>
  )
}

const UserProfilePage = () => {
  const theme = useTheme();
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'));

  return (
    <Grid container minHeight='100vh'>
      <Grid size={{xs: 12, lg: 3}}>
        {isLgScreen ? (<></>) : (<Navbar/>)}
        <UserDataPanel/>
      </Grid>
      <Grid size={{xs: 12, lg: 9}}>
        <Box sx={{minHeight: '100%'}}>
          {isLgScreen ? (<Navbar/>) : (<></>)}
        </Box>
      </Grid>
    </Grid>
  )
}

export default UserProfilePage