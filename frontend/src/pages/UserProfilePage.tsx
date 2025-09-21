import { Grid, Box, Avatar, Typography, Container } from '@mui/material'
import basicAvatar from '../assets/examples/basic-avatar.png'
import SchoolIcon from '@mui/icons-material/School';
import PublicIcon from '@mui/icons-material/Public';
import Navbar from '../components/Navbar';
import { useTheme, useMediaQuery } from '@mui/material';
import Button from '@mui/material/Button';
import PersonAdd from '@mui/icons-material/PersonAdd';
import PersonRemove from '@mui/icons-material/PersonRemove';
import SendIcon from '@mui/icons-material/Send';
import Stack from '@mui/material/Stack';
import UniversityReview, { type UniversityReviewProps } from '../components/UniversityReview';
import { sendGetUserProfileRequest, sendGetUserReviewsRequest } from '../utils/user';
import { useEffect, useState } from 'react';
import type { GetUserProfileResponse } from '../dtos/user/GetUserProfileResponse';
import { useNavigate, useParams } from 'react-router-dom';
import { sendFollowUserRequest, sendUnfollowUserRequest } from '../utils/follow';
import { getLocalDate } from '../utils/date-utils';
import NoReviewsFrame from '../components/NoReviewsFrame';

type ActionButtonsProps = {
  userId: string | number;
  isFollowed: boolean;
  setIsFollowed: (value: boolean) => void;
}

const ActionButtons = (props : ActionButtonsProps) => {
  const navigate = useNavigate()
  const handleFollow = async () => {
    if (props.userId) {
      props.setIsFollowed(true)
      await sendFollowUserRequest(props.userId)
    }
  }
  const handleUnfollow = async () => {
    if (props.userId) {
      props.setIsFollowed(false)
      await sendUnfollowUserRequest(props.userId)
    }
  }

  return (
    <Stack direction='row' spacing={2} sx={{marginTop: 3}}>
      {props.isFollowed ? (
        <Button variant='outlined' endIcon={<PersonRemove/>} 
          onClick={() => {handleUnfollow()}}>
          UNFOLLOW
        </Button>
      ): (
        <Button variant='outlined' endIcon={<PersonAdd/>}
          onClick={() => {handleFollow()}}>
          FOLLOW
        </Button>
      )}
      <Button variant='contained' endIcon={<SendIcon/>}
        onClick={() => {
          if (props.userId) {
            navigate(`/chat/${props.userId}`)
          }
        }}>
        SEND MESSAGE
      </Button>
    </Stack>
  );
}

type UserDataPanelProps = {
  userId: number | string;
  isOwnProfile: boolean;
}

const UserDataPanel = (props: UserDataPanelProps) => {
  const theme = useTheme();
  const navigate = useNavigate()
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'));
  const [nick, setNick] = useState<string>()
  const [countryName, setCountryName] = useState<string>()
  const [countryFlag, setCountryFlag] = useState<string | null>()
  const [homeUniversityName, setHomeUniversityName] = useState<string>()
  const [userDescription, setUserDescription] = useState<string>('')
  const [isFollowed, setIsFollowed] = useState<boolean>(false)

  const getData = async () => {
    if (!props.userId) {
      navigate('/not-found')
      return
    }
    const result = await sendGetUserProfileRequest(props.userId)
    if (result.isSuccess) {
      const data: GetUserProfileResponse = result.data
      const universityName = data.homeUniversity ?
        (data.homeUniversity.englishName ? data.homeUniversity.englishName : data.homeUniversity.nativeName) :
        ('no info about university')
      setNick(data.nick)
      setHomeUniversityName(universityName)
      setCountryName(data.countryOfOrigin ? data.countryOfOrigin.name : 'no info about country')
      setUserDescription(data.description)
      setCountryFlag(data.countryOfOrigin ? `/flags/${data.countryOfOrigin?.name}.png` : null)
      setIsFollowed(data.isFollowed)
      console.log(data)
    } else {
      if (result.error.status === 'NOT_FOUND') {
        navigate('/not-found')
      }
    }
  }

  useEffect(() => {
    getData()
  }, [])

  return (
    <>
      {isLgScreen ? (
        <Box sx={{ backgroundColor: '#182c44', minHeight: '100vh', display: 'flex', 
          flexDirection: 'column', alignItems: 'center', paddingY: 4}}>
          <Avatar alt='User avatar' src={basicAvatar} sx={{
            width: '20vh', 
            height: '20vh'}}/>
          <Typography sx={{color: 'white', fontSize: {lg: '2rem'}, fontWeight: '700', 
            paddingTop: {lg: 2}}}>
            {nick}
          </Typography>
          <Box sx={{width: '100%', paddingLeft: {lg: 6}, paddingTop: {lg: 2}}}>
            <Box sx={{display: 'flex'}}>
              <PublicIcon sx={{color: 'white'}}/>
              <Typography sx={{color: 'white', fontSize: {lg: '1.2rem'}, fontWeight: '600',
                paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}, marginLeft: 1}}>
                {countryName}
              </Typography>
              {countryFlag ? (
                <img src={countryFlag} alt='' style={{height: '1rem', marginTop: 4}}/>
              ) : (
                <></>
              )}
            </Box>
            <Box sx={{display: 'flex'}}>
              <SchoolIcon sx={{color: 'white'}}/>
              <Typography sx={{color: 'white', fontSize: {lg: '1rem'}, fontWeight: '500',
                marginLeft: 1}}>
                {homeUniversityName}
              </Typography>
            </Box>
          </Box>
            {!props.isOwnProfile ? (
              <ActionButtons userId={props.userId} isFollowed={isFollowed} 
                setIsFollowed={setIsFollowed}/>
            ) : (
              <></>
            )}
          <Box sx={{width: '100%', paddingX: {lg: 5}, paddingTop: {lg: 4}}}>
            {userDescription.trim() !== '' ? 
              <>
                <Typography sx={{color: 'white', fontSize: {lg: '1.2rem'}, fontWeight: '600',
                    paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}}}>
                  About
                </Typography>
                <Typography sx={{color: 'white', fontSize: {lg: '0.8rem'}, fontWeight: '400',
                  paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}}}>
                  {userDescription}
                </Typography>
              </> : 
              <></>
            }
          </Box>
        </Box> 
      ) : (
        <Box sx={{ backgroundColor: '#182c44', display: 'flex', 
          flexDirection: 'column', paddingY: 2}}>
          <Box sx={{display: 'flex', flexDirection: 'row', paddingLeft: 3}}>
            <Avatar alt='User avatar' src={basicAvatar} sx={{
              width: '10vh', 
              height: '10vh',
              marginRight: 3,
              marginTop: 1}}/>
            <Box sx={{display: 'flex', flexDirection: 'column'}}>
              <Typography sx={{color: 'white', fontSize: '1rem', fontWeight: '700', 
                paddingY: 0.4}}>
                {nick}
              </Typography>
              <Box sx={{display: 'flex'}}>
                <PublicIcon sx={{color: 'white'}}/>
                <Typography sx={{color: 'white', fontSize: {lg: '0.7rem'}, fontWeight: '600',
                  paddingBottom: 0.5, marginLeft: 1, marginRight: 1}}>
                  {countryName}
                </Typography>
                {countryFlag ? (
                  <img src={countryFlag} style={{height: '1rem', marginTop: 4}}/>
                ) : (
                  <></>
                )}
              </Box>
              <Box sx={{display: 'flex'}}>
                <SchoolIcon sx={{color: 'white'}}/>
                <Typography sx={{color: 'white', fontSize: {lg: '1rem'}, fontWeight: '500',
                  marginLeft: 1}}>
                  {homeUniversityName}
                </Typography>
              </Box>
            </Box>
          </Box>
          <Container sx={{marginY: 0.5}}>
            {!props.isOwnProfile ? (
              <ActionButtons userId={props.userId} isFollowed={isFollowed} 
                setIsFollowed={setIsFollowed}/>
            ) : (
              <></>
            )}
          </Container>
          <Box sx={{paddingX: 3, paddingTop: 4}}>
            {userDescription.trim() !== '' ? 
              <>
              <Typography sx={{color: 'white', fontWeight: '600',
                  paddingBottom: 1}}>
                About
              </Typography>
              <Typography sx={{color: 'white', fontWeight: '400',
                paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}}}>
                {userDescription}
              </Typography>
              </> : 
              <></>
            }
          </Box>
        </Box> 
      )}
    </>
  )
}

type FeedPanelProps = {
  userId: string | number;
  isOwnProfile: boolean;
}

const FeedPanel = (props: FeedPanelProps) => {
  const [reviewProps, setReviewsProps] = useState<UniversityReviewProps[]>([])
  const getData = async () => {
    const result = await sendGetUserReviewsRequest(props.userId)
    if (result.isSuccess) {
      const props: UniversityReviewProps[]  = result.data.map(r => ({
        id: r.id,
        title: r.university.englishName ? r.university.englishName : r.university.nativeName,
        subheader: getLocalDate(r.createdAt),
        starRating: r.starRating,
        textContent: r.textContent,
        reactions: r.reactions
      }))
      setReviewsProps(props)
    }
  }

  useEffect(() => {
    getData()
  }, [])

  return (
    <>
      {reviewProps.length != 0 ? 
        (
          <>
            <Typography sx={{fontSize: {xs: '1.3rem', lg: '1.7rem'}, fontWeight: 600 , 
              paddingY: 2, paddingLeft: {xs: 2, lg: 4}}}>
              University reviews
            </Typography>
            <Container sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 3}}>
              {reviewProps.map(rp => (<UniversityReview {...rp}/>))}
          </Container>
          </>
        ) : (
          <NoReviewsFrame isOwnProfile={props.isOwnProfile}/>
        )}
    </>
  )
}

const UserProfilePage = () => {
  const navigate = useNavigate()
  const theme = useTheme();
  const { userId } = useParams()
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'));
  const signedInUserId: string | null = localStorage.getItem('userId')
  const isOwnProfile = userId === signedInUserId
  
  if (!userId) {
    navigate('/not-found')
    return
  }

  return (
    <Grid container minHeight='100vh' sx={{backgroundColor: '#eeececff'}}>
      <Grid size={{xs: 12, lg: 3}}>
        {isLgScreen ? (<></>) : (<Navbar/>)}
        <UserDataPanel userId={userId} isOwnProfile={isOwnProfile}/>
        {isLgScreen ? (<></>) : (<FeedPanel userId={userId} isOwnProfile={isOwnProfile}/>)}
      </Grid>
      <Grid size={{xs: 0, lg: 9}}>
        <Box sx={{minHeight: '100%'}}>
          {isLgScreen ? (
            <>
              <Navbar/>
              <FeedPanel userId={userId} isOwnProfile={isOwnProfile}/>
            </>
          ) : (
            <></>
          )}
        </Box>
      </Grid>
    </Grid>
  )
}

export default UserProfilePage