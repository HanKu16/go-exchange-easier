import { Box, CircularProgress, Typography } from '@mui/material'

export type LoadingContentProps = {
  title: string;
}

const LoadingContent = (props: LoadingContentProps) => {
  return (
    <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', 
      justifyContent: 'center', minHeight: 200, textAlign: 'center',
      color: 'text.secondary', p: 3}}>
      <CircularProgress size={40} sx={{color: '#1f3958ff',
          marginBottom: '40px'}}/>
      <Typography variant="h6" gutterBottom>
        {props.title}
      </Typography>
    </Box>
  )
}

export default LoadingContent
