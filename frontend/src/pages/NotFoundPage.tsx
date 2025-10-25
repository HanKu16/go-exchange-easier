import { Box, Typography, type SvgIconProps } from '@mui/material'
import Navbar from '../components/Navbar'
import type { ElementType } from 'react'

type NotFoundPageProps = {
  icon: ElementType<SvgIconProps>; 
  title: string;
  subheader: string;
}

const NotFoundPage = (props: NotFoundPageProps) => {
  return (
    <Box sx={{minHeight: '100vh', backgroundColor: '#eeececff',
        display: 'flex', flexDirection: 'column' }}>
      <Navbar/>
      <Box sx={{flexGrow: 1, p: 4, textAlign: 'center', display: 'flex', 
          flexDirection: 'column', justifyContent: 'center', alignItems: 'center'}}>
        <props.icon sx={{fontSize: 120, color: 'error.main', mb: 3}}/>
        <Typography variant='h3' component='h1' gutterBottom>
          {props.title}
        </Typography>
        <Typography variant='h6' color='text.secondary' sx={{mb: 4}}>
          {props.subheader}
        </Typography>
      </Box>
    </Box>
  )
}

export default NotFoundPage