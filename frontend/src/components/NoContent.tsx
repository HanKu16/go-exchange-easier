import { Box, Typography } from '@mui/material'
import RateReviewIcon from "@mui/icons-material/RateReview";

export type NoContentProps = {
  title: string;
  subheader: string;
}

const NoContent = (props: NoContentProps) => {
  return (
    <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', 
      justifyContent: 'center', minHeight: 200, textAlign: 'center',
      color: 'text.secondary', p: 3}}>
      <RateReviewIcon sx={{ fontSize: 60, mb: 2, color: "grey.500" }} />
      <Typography variant="h6" gutterBottom>
        {props.title}
      </Typography>
      <Typography variant="body2">
        {props.subheader}
      </Typography>
    </Box>
  );
}

export default NoContent
