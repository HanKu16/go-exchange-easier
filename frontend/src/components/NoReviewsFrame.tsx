import { Box, Typography } from '@mui/material'
import RateReviewIcon from "@mui/icons-material/RateReview";

export type NoReviewsFrameProps = {
  isOwnProfile: boolean;
}

const NoReviewsFrame = (props: NoReviewsFrameProps) => {
  return (
    <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', 
      justifyContent: 'center', minHeight: 200, textAlign: 'center',
      color: 'text.secondary', p: 3}}>
      <RateReviewIcon sx={{ fontSize: 60, mb: 2, color: "grey.500" }} />
      <Typography variant="h6" gutterBottom>
        No reviews yet
      </Typography>
      <Typography variant="body2">
        {props.isOwnProfile ? 
          "You haven't written any reviews." : 
          "This user hasn't written any reviews."
        }
      </Typography>
    </Box>
  );
}

export default NoReviewsFrame