import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import ThumbUp from '@mui/icons-material/ThumbUp';
import ThumbDown from '@mui/icons-material/ThumbDown';
import { Rating } from '@mui/material';
import basicAvatar from '../assets/examples/basic-avatar.png'

const UniversityReview = () => {
  return (
    <Card sx={{ width: '95%' }}>
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe" src={basicAvatar}/>
        }
          action={
    <IconButton aria-label="add to favorites">
      <Rating name="read-only" value={3} readOnly />
    </IconButton>
  }
        title="University of Bologna"
        subheader="September 14, 2016"
      />
      <CardContent>
        <Typography variant="body2" sx={{ color: 'text.secondary' }}>
          This impressive paella is a perfect party dish and a fun meal to cook
          together with your guests. Add 1 cup of frozen peas along with the mussels,
          if you like.
        </Typography>
      </CardContent>
      <CardActions disableSpacing >
        <IconButton aria-label="like review">
          <ThumbUp sx={{marginRight: 0.5}}/>
          <Typography sx={{fontSize: '0.9rem'}}>632</Typography>
        </IconButton>
        <IconButton aria-label="dislike review">
          <ThumbDown sx={{marginRight: 0.5}}/>
          <Typography sx={{fontSize: '0.9rem'}}>412</Typography>
        </IconButton>
      </CardActions>
    </Card>
  );
}

export default UniversityReview