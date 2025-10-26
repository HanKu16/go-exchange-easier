import { Grid, Box, Avatar, Typography, Container } from '@mui/material'
import basicAvatar from '../assets/examples/basic-avatar.png'
import SchoolIcon from '@mui/icons-material/School'
import PublicIcon from '@mui/icons-material/Public'
import Navbar from '../components/Navbar'
import { useTheme, useMediaQuery } from '@mui/material'
import Button from '@mui/material/Button'
import PersonAdd from '@mui/icons-material/PersonAdd'
import PersonRemove from '@mui/icons-material/PersonRemove'
import SendIcon from '@mui/icons-material/Send'
import Stack from '@mui/material/Stack'
import UniversityReview, { type UniversityReviewProps } from '../components/UniversityReview'
import { getSignedInUserId, sendGetUserExchangesRequest, sendGetUserProfileRequest, 
  sendGetUserReviewsRequest } from '../utils/user'
import { useEffect, useState } from 'react'
import type { GetUserProfileResponse } from '../dtos/user/GetUserProfileResponse'
import { useNavigate, useParams } from 'react-router-dom'
import { sendFollowUserRequest, sendUnfollowUserRequest } from '../utils/follow'
import { getLocalDate } from '../utils/date-utils'
import NoContent from '../components/NoContent'
import AddIcon from '@mui/icons-material/Add'
import type { ExchangesProps } from '../components/Exchanges'
import Exchanges from '../components/Exchanges'
import { isInteger } from '../utils/number-utils'
import NotFoundPage from './NotFoundPage'
import PersonOffIcon from '@mui/icons-material/PersonOff'
import ServerErrorPage from './ServerErrorPage'
import ServiceUnavailablePage from './ServiceUnavailablePage'
import LoadingPage from './LoadingPage'
import type { DataFetchStatus } from '../types/DataFetchStatus'
import ContentLoadError from '../components/ContentLoadError'
import LoadingContent from '../components/LoadingContent'
import { useSnackbar } from '../context/SnackBarContext'

const AddExchangeButton = () => {
  const navigate = useNavigate()

  return (
    <Box sx={{display: 'flex', justifyContent: 'flex-start', mb: 2,
      width: '91%', margin: 'auto'}}>
      <Button variant='contained' startIcon={<AddIcon />}
        onClick={() => navigate('/me')}
        sx={{borderRadius: 2, textTransform: 'none', fontWeight: 'bold', 
          marginTop: 2, px: 3, py: 1, backgroundColor: '#04315f',
          '&:hover': {
            backgroundColor: '#064080'
          }}}>
        Add exchange
      </Button>
    </Box>
  );
}

type ActionButtonsProps = {
  userId: string | number;
  isFollowed: boolean;
  setIsFollowed: (value: boolean) => void;
}

const ActionButtons = (props : ActionButtonsProps) => {
  const navigate = useNavigate()
  const { showAlert } = useSnackbar()

    const handleFollow = async () => {
        if (props.userId) {
            props.setIsFollowed(true);
            const result = await sendFollowUserRequest(props.userId)
            if (!result.isSuccess) {
              props.setIsFollowed(false)
              showAlert('An error occured. Try follow user later.', 'error')
            }
        }
    }

    const handleUnfollow = async () => {
        if (props.userId) {
            props.setIsFollowed(false);
            const result = await sendUnfollowUserRequest(props.userId);
            if (!result.isSuccess) {
              props.setIsFollowed(true)
              showAlert('An error occured. Try unfollow user later.', 'error')
            }
        }
    }

  return (
    <Stack direction='row' spacing={2} sx={{marginTop: 3}}>
      {props.isFollowed ? (
        <Button variant='outlined' endIcon={<PersonRemove/>} 
          onClick={() => handleUnfollow()}>
          UNFOLLOW
        </Button>
      ): (
        <Button variant='outlined' endIcon={<PersonAdd/>}
          onClick={() => handleFollow()}>
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
  nick: string;
  countryName: string | null;
  homeUniversityName?: string;
  description: string;
  isFollowed: boolean;
  isOwnProfile: boolean;
}

const UserDataPanel = (props: UserDataPanelProps) => {
  const theme = useTheme()
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'))
  const countryFlag = props.countryName ? `/flags/${props.countryName}.png` : null
  const [isFollowed, setIsFollowed] = useState<boolean>(props.isFollowed)

  return (
    <>
      {isLgScreen ? (
        <Box sx={{ backgroundColor: '#182c44', minHeight: '100vh', display: 'flex', 
          flexDirection: 'column', alignItems: 'center', paddingY: 4,
          position: 'fixed', width: 'inherit'}}>
          <Avatar alt='User avatar' src={basicAvatar} sx={{
            width: '20vh', 
            height: '20vh'}}/>
          <Typography sx={{color: 'white', fontSize: {lg: '2rem'}, fontWeight: '700', 
            paddingTop: {lg: 2}}}>
            {props.nick}
          </Typography>
          <Box sx={{width: '100%', paddingLeft: {lg: 6}, paddingTop: {lg: 2}}}>
            <Box sx={{display: 'flex'}}>
              <PublicIcon sx={{color: 'white'}}/>
              <Typography sx={{color: 'white', fontSize: {lg: '1.2rem'}, fontWeight: '600',
                paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}, marginLeft: 1}}>
                {props.countryName}
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
                {props.homeUniversityName}
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
            {props.description.trim() !== '' ? 
              <>
                <Typography sx={{color: 'white', fontSize: {lg: '1.2rem'}, fontWeight: '600',
                    paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}}}>
                  About
                </Typography>
                <Typography sx={{color: 'white', fontSize: {lg: '0.8rem'}, fontWeight: '400',
                  paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}}}>
                  {props.description}
                </Typography>
              </> : 
              <></>
            }
          </Box>
        </Box> 
      ) : (
        <Box sx={{backgroundColor: '#182c44', display: 'flex', 
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
                {props.nick}
              </Typography>
              <Box sx={{display: 'flex'}}>
                <PublicIcon sx={{color: 'white'}}/>
                <Typography sx={{color: 'white', fontSize: {lg: '0.7rem'}, fontWeight: '600',
                  paddingBottom: 0.5, marginLeft: 1, marginRight: 1}}>
                  {props.countryName}
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
                  {props.homeUniversityName}
                </Typography>
              </Box>
            </Box>
          </Box>
          <Container sx={{marginY: 0.5}}>
            {!props.isOwnProfile ? (
              <ActionButtons userId={props.userId} isFollowed={props.isFollowed} 
                setIsFollowed={setIsFollowed}/>
            ) : (
              <></>
            )}
          </Container>
          <Box sx={{paddingX: 3, paddingTop: 4}}>
            {props.description.trim() !== '' ? 
              <>
              <Typography sx={{color: 'white', fontWeight: '600',
                  paddingBottom: 1}}>
                About
              </Typography>
              <Typography sx={{color: 'white', fontWeight: '400',
                paddingBottom: {lg: 0.5}, paddingRight: {lg: 1}}}>
                {props.description}
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
  const [exchangesProps, setExchangesProps] = useState<ExchangesProps | undefined>(undefined)
  const [reviewsFetchStatus, setReviewsFetchStatus] = useState<DataFetchStatus>('loading')
  const [exchangesFetchStatus, setExchangesFetchStatus] = useState<DataFetchStatus>('loading')

  const getReviews = async () => {
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
      setReviewsFetchStatus('success')
    } else {
      switch (result.error.status) {
        case 'INTERNAL_SERVER_ERROR':
          setReviewsFetchStatus('serverError')
          break
        case 'SERVICE_UNAVAILABLE':
          setReviewsFetchStatus('connectionError')
          break
      }
    }
  }

  const getExchanges = async () => {
    const result = await sendGetUserExchangesRequest(props.userId)
    if (result.isSuccess) {
      const props: ExchangesProps = {
        exchanges: result.data.map(e => ({
          id: e.id,
          timeRange: {
            startedAt: e.timeRange.startedAt,
            endAt: e.timeRange.endAt
          },
          university: {
            id: e.university.id,
            name: e.university.englishName
              ? e.university.englishName
              : e.university.nativeName
          },
          universityMajorName: e.universityMajor.name,
          city: {
            name: e.city.name,
            countryName: e.city.country.name
          }
        }))
      }
      setExchangesProps(props)
      setExchangesFetchStatus('success')
    } else {
      switch (result.error.status) {
        case 'INTERNAL_SERVER_ERROR':
          setReviewsFetchStatus('serverError')
          break
        case 'SERVICE_UNAVAILABLE':
          setReviewsFetchStatus('connectionError')
          break
      }
    }
  }

  const getReviewsContent = () => {
    if (reviewsFetchStatus === 'success') {
      if (reviewProps.length !== 0) {
        return (
          <Container sx={{display: 'flex', flexDirection: 'column', 
            alignItems: 'center', gap: 3}}>
              {reviewProps.map(rp => (<UniversityReview {...rp}/>))}
          </Container>
        )
      } else {
        return (
          <NoContent title={'No reviews yet'} 
            subheader={props.isOwnProfile ? 
              "You haven't written any reviews." :
              "This user hasn't written any reviews."
          }/>
        )
      }
    } else if (reviewsFetchStatus === 'loading') {
      return <LoadingContent title='Loading reviews'/>
    } else if (reviewsFetchStatus === 'connectionError') {
      return <ContentLoadError title='Connection error' subheader=
          'An error occurred while fetching reviews.'/>
    } else if (reviewsFetchStatus === 'serverError') {
      return <ContentLoadError title='Server error' subheader=
          'An error occurred while fetching reviews.'/>
    }
  }

  const getExchangesContent = () => {
    if (exchangesFetchStatus === 'success') {
      if ((exchangesProps !== undefined) && (exchangesProps.exchanges.length !== 0)) {
        return (
          <>
            <Exchanges {...exchangesProps}/>
            {props.isOwnProfile && <AddExchangeButton/>}
          </>)
      } else {
        return (
          <>
            {props.isOwnProfile ? 
              <AddExchangeButton/> : 
              <NoContent title='No exchanges yet' subheader=
                "This user hasn't add any exchange."/>}
          </>
        )
      }
    } else if (exchangesFetchStatus === 'loading') {
      return <LoadingContent title='Loading exchanges'/>
    } else if (exchangesFetchStatus === 'connectionError') {
      return <ContentLoadError title='Connection error' subheader=
          'An error occurred while fetching exchanges.'/>
    } else if (exchangesFetchStatus === 'serverError') {
      return <ContentLoadError title='Server error' subheader=
          'An error occurred while fetching exchanges.'/>
    }
  }

  useEffect(() => {
    getReviews()
    getExchanges()
  }, [])

  return (
    <Box sx={{paddingBottom: 4}}>
      <Typography sx={{fontSize: {xs: '1.3rem', lg: '1.7rem'}, fontWeight: 600 , 
        paddingY: 2.5, paddingLeft: {xs: 2, lg: 4}}}>
        Exchange history
      </Typography>
      {getExchangesContent()}
      <Typography sx={{fontSize: {xs: '1.3rem', lg: '1.7rem'}, fontWeight: 600 , 
        paddingY: 2.5, paddingLeft: {xs: 2, lg: 4}}}>
        University reviews
      </Typography>
      {getReviewsContent()}
    </Box>
  )
}

type UserProfileFetchStatus = 
  DataFetchStatus |
  'userNotFound'

const UserProfilePage = () => {
  const theme = useTheme();
  const { userId } = useParams()
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'));
  const signedInUserId: string = getSignedInUserId()
  const isOwnProfile = userId === signedInUserId
  const [userProfileFetchStatus, setUserProfileFetchStatus] = 
    useState<UserProfileFetchStatus>('loading')
  const [userDataPanelProps, setUserDataPanelProps] = useState<UserDataPanelProps>()
  
  if (!userId || !isInteger(userId)) {
    return <NotFoundPage icon={PersonOffIcon} title='User not found'
      subheader='Profile you are looking for was deleted or does not exist'/>
  }

  const getData = async () => {
    if (userId === undefined) {
      setUserProfileFetchStatus('userNotFound')
      return
    }
    const result = await sendGetUserProfileRequest(userId)
    if (result.isSuccess) {
      const data: GetUserProfileResponse = result.data
      const universityName = data.homeUniversity ?
        (data.homeUniversity.englishName ? 
          data.homeUniversity.englishName : data.homeUniversity.nativeName) :
          ('no info about university')
      const userDataPanelProps: UserDataPanelProps = {
        userId: data.userId,
        nick: data.nick,
        countryName: data.countryOfOrigin ? 
          data.countryOfOrigin.name : 'no info about country',
        homeUniversityName: universityName,
        description: data.description,
        isFollowed: data.isFollowed,
        isOwnProfile: isOwnProfile
      }
      setUserDataPanelProps(userDataPanelProps)
      setUserProfileFetchStatus('success')
    } else {
      if (result.error.status === 'NOT_FOUND') {
        setUserProfileFetchStatus('userNotFound')
      } else if (result.error.status === 'INTERNAL_SERVER_ERROR') {
        setUserProfileFetchStatus('serverError')
      } else if (result.error.status === 'SERVICE_UNAVAILABLE') {
        setUserProfileFetchStatus('connectionError')
      }
    }
  }

  useEffect(() => {
    getData()
  }, [])
  
  if (userProfileFetchStatus === 'userNotFound') {
    return <NotFoundPage icon={PersonOffIcon} title='User not found'
      subheader='Profile you are looking for was deleted or does not exist'/>
  } else if (userProfileFetchStatus === 'connectionError') {
    return <ServiceUnavailablePage/>
  } else if (userProfileFetchStatus === 'serverError') {
    return <ServerErrorPage/>
  } else if (userProfileFetchStatus === 'loading') {
    return <LoadingPage backgroundColor='#eeececff' circularProgressColor='#182c44'
      text='Loading user profile'/>
  } else if (userProfileFetchStatus === 'success' && userDataPanelProps !== undefined) {
    return (
      <Grid container minHeight='100vh' sx={{backgroundColor: '#eeececff'}}>
        <Grid size={{xs: 12, lg: 3}}>
          {isLgScreen ? (<></>) : (<Navbar/>)}
          <UserDataPanel {...userDataPanelProps}/>
          {isLgScreen ? (<></>) : (<FeedPanel userId={userId} isOwnProfile={isOwnProfile}/>)}
        </Grid>
        <Grid size={{xs: 0, lg: 9}}>
          <Box sx={{minHeight: '100%'}}>
            {isLgScreen &&
              <>
                <Navbar/>
                <FeedPanel userId={userId} isOwnProfile={isOwnProfile}/>
              </>
            }
          </Box>
        </Grid>
      </Grid>
    )
  }
}

export default UserProfilePage