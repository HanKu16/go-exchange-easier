import { Box, Typography } from '@mui/material'
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline'

export type ContentLoadErrorProps = {
  title: string;
  subheader: string;
}

const ContentLoadError = (props: ContentLoadErrorProps) => {
  return (
    <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', 
      justifyContent: 'center', minHeight: 200, textAlign: 'center',
      color: 'text.secondary', p: 3}}>
      <ErrorOutlineIcon sx={{fontSize: 60, mb: 2, color: '#f05252ff'}} />
      <Typography variant="h6" gutterBottom>
        {props.title}
      </Typography>
      <Typography variant="body2">
        {props.subheader}
      </Typography>
    </Box>
  );
}

export default ContentLoadError
