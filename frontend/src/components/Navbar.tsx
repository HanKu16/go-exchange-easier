import { Box, Avatar, Typography } from '@mui/material'
import MenuIcon from '@mui/icons-material/Menu'
import AppBar from '@mui/material/AppBar'
import Toolbar from '@mui/material/Toolbar'
import IconButton from '@mui/material/IconButton'
import Menu from '@mui/material/Menu'
import Container from '@mui/material/Container'
import Button from '@mui/material/Button'
import Tooltip from '@mui/material/Tooltip'
import MenuItem from '@mui/material/MenuItem'
import { useState } from 'react'
import earthImage from '../assets/registration_page/earth.png'
import basicAvatar from '../assets/examples/basic-avatar.png'
import { useTheme, useMediaQuery } from '@mui/material';
import { useNavigate } from 'react-router-dom'

type NavbarItem = {
  label: string;
  route: string;
}

const userId = localStorage.getItem('userId')

const navbarItems: NavbarItem[] = [
  {label: 'Profile', route: `/users/${userId}/profile`},
  {label: 'Account', route: '/me'},
  {label: 'Chat', route: '/chat'},
  {label: 'Search', route: '/search'},
  {label: 'Follows', route: '/follows'}
]

const settings = ['Account', 'Edit', 'Logout'];

const Navbar = () => {
  const navigate = useNavigate()
  const [anchorElNav, setAnchorElNav] = useState<null | HTMLElement>(null);
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const theme = useTheme();
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'));

  const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    <AppBar position="static">
      <Container maxWidth="xl" sx={{backgroundColor: {xs: '#182c44', lg: '#4a4a4aff'}}}>
        <Toolbar disableGutters>
          {isLgScreen ? (
            <img src={earthImage}
              alt="Earth image"
              style={{height: '2.5rem', width: 'auto', marginRight: 14}}/>
            ) : (
              <></>
            )
          }
          <Box sx={{flexGrow: 1, display: {xs: 'flex', md: 'none'}}}>
            <IconButton size="large" aria-label="account of current user"
              aria-controls="menu-appbar" aria-haspopup="true"
              onClick={handleOpenNavMenu} color="inherit">
              <MenuIcon />
            </IconButton>
            <Menu id="menu-appbar" anchorEl={anchorElNav} anchorOrigin={{
              vertical: 'bottom', horizontal: 'left'}} keepMounted
              transformOrigin={{vertical: 'top', horizontal: 'left'}}
              open={Boolean(anchorElNav)} onClose={handleCloseNavMenu}
              sx={{display: {xs: 'block', md: 'none'}}}>
              {navbarItems.map(item => (
                <MenuItem key={item.label} onClick={() => {
                  navigate(item.route)
                  window.location.reload()
                }}>
                  <Typography sx={{textAlign: 'center'}}>{item.label}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
            {navbarItems.map(item => (
              <Button
                key={item.label}
                onClick={() => {
                  navigate(item.route)
                  window.location.reload()
                }}
                sx={{my: 2, color: 'white', display: 'block'}}
              >
                {item.label}
              </Button>
            ))}
          </Box>
          <Box sx={{flexGrow: 0}}>
            <Tooltip title='Open settings'>
              <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
                <Avatar alt='User avatar' src={basicAvatar} sx={{width: '2.5rem', height: '2.5rem'}}/>
              </IconButton>
            </Tooltip>
            <Menu sx={{mt: '45px'}} id='menu-appbar' anchorEl={anchorElUser}
              anchorOrigin={{vertical: 'top', horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{vertical: 'top', horizontal: 'right'}}
              open={Boolean(anchorElUser)} onClose={handleCloseUserMenu}>
              {settings.map((setting) => (
                <MenuItem key={setting} onClick={handleCloseUserMenu}>
                  <Typography sx={{textAlign: 'center'}}>{setting}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}

export default Navbar