import { Box, Typography, Grid } from '@mui/material'
import { useTheme, useMediaQuery } from '@mui/material'
import { Button } from '@mui/material'
import { useEffect, useState } from 'react'
import LanguageIcon from '@mui/icons-material/Language'
import { useNavigate, useParams } from 'react-router-dom'
import Navbar from '../components/Navbar'
import PlaceIcon from '@mui/icons-material/Place';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import { sendGetReviewsCountRequest, sendGetUniversityProfileRequest, 
  sendGetUniversityReviewsRequest } from '../utils/university'
import { Container } from '@mui/material'
import Link from '@mui/material/Link'
import { Pagination } from '@mui/material'
import type { UniversityReviewProps } from '../components/UniversityReview'
import { getLocalDate } from '../utils/date-utils'
import NoContent from '../components/NoContent'
import UniversityReview from '../components/UniversityReview'
import { sendFollowUniversityRequest, sendUnfollowUniversityRequest } from '../utils/follow'

type UniversityDataPanelProps = {
  userId: number | string | null;
  universityId: number | string | undefined;
}

type FollowButtonProps = {
  universityId: number | string | undefined;
  isFollowed: boolean;
  setIsFollowed: (value: boolean) => void;
}

const FollowButton = (props: FollowButtonProps) => {
  const handleFollow = async () => {
    if (props.universityId) {
      props.setIsFollowed(true)
      const result = await sendFollowUniversityRequest(props.universityId)
      if (!result.isSuccess) {
        props.setIsFollowed(false)
      }
    }
  }
  const handleUnfollow = async () => {
    if (props.universityId) {
      props.setIsFollowed(false)
      const result = await sendUnfollowUniversityRequest(props.universityId)
      if (!result.isSuccess) {
        props.setIsFollowed(true)
      }
    }
  }

  return (
    <Container sx={{display: 'flex', justifyContent: {lg: 'center'}, marginTop: {xs: 2, lg: 4}}}>
      {props.isFollowed ? (
        <Button variant='outlined' size='medium' endIcon={<BookmarkIcon/>} 
          onClick={handleUnfollow}>
          UNFOLLOW
        </Button>
      ): (
        <Button variant='contained' size='medium' endIcon={<BookmarkIcon/>}
          onClick={handleFollow}>
          FOLLOW
        </Button>
      )}
    </Container>
  )
}

const UniversityDataPanel = (props: UniversityDataPanelProps) => {
  const theme = useTheme();
  const navigate = useNavigate()
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'));
  const [universityId, setUniversityId] = useState<number>()
  const [nativeName, setNativeName] = useState<string>()
  const [englishName, setEnglishName] = useState<string | null>()
  const [linkToWebsite, setLinkToWebsite] = useState<string | null>()
  const [cityName, setCityName] = useState<string>()
  const [countryName, setCountryName] = useState<string>()
  const [isFollowed, setIsFollowed] = useState<boolean>(false)

  const getUniversityProfileData = async () => {
    if (!props.universityId) {
      navigate('/not-found')
      return
    }
    const result = await sendGetUniversityProfileRequest(props.universityId)
    if (result.isSuccess) {
      setUniversityId(result.data.id)
      setNativeName(result.data.nativeName)
      setEnglishName(result.data.englishName)
      setLinkToWebsite(result.data.linkToWebsite)
      setCityName(result.data.cityName)
      setCountryName(result.data.countryName)
      setIsFollowed(result.data.isFollowed)
    } else {
      // ...
    }
  }

  useEffect(() => {
    getUniversityProfileData()
  }, [])

  return (
    <>
      {isLgScreen ? (
        <Container sx={{ backgroundColor: '#182c44', minHeight: '100vh', display: 'flex', 
          flexDirection: 'column', alignItems: 'center', paddingY: 8,
          position: 'fixed', width: 'inherit'}}>
          <img alt='Country flag' src={`/flags/${countryName}.png`} 
            style={{height: '8rem', marginBottom: 8}}/>
          <Typography sx={{color: 'white', fontSize: '1.7rem', fontWeight: '700', 
            paddingTop: 2, textAlign: 'center'}}>
            {nativeName}
          </Typography>
          {englishName &&
            <Typography sx={{color: '#e6e2e2ff', fontSize: '1.1rem', fontWeight: '700', 
              textAlign: 'center', marginTop: 1}}>
              {englishName}
            </Typography>
          }
          <Container sx={{width: '100%', paddingTop: 3}}>
            <Box sx={{display: 'flex'}}>
              <PlaceIcon sx={{color: 'white', fontSize: '2rem'}}/>
              <Typography sx={{color: 'white', fontSize: '1.5rem', fontWeight: '600',
                marginLeft: 1}}>
                {cityName}, {countryName}
              </Typography>
            </Box>
            {linkToWebsite && 
              <Box sx={{display: 'flex', marginTop: 1, marginLeft: 0.5}}>
                <LanguageIcon sx={{color: 'white', fontSize: '1.7rem'}}/>    
                <Link href={linkToWebsite} underline="hover"
                  color="primary" sx={{fontWeight: 500,
                    fontSize: '1.2rem', marginLeft: 1.5,
                    "&:hover": {color: "secondary.main"}}}>
                  Go to website
                </Link>
              </Box>
            }
            <FollowButton universityId={universityId} isFollowed={isFollowed}
              setIsFollowed={setIsFollowed}/>
          </Container>          
        </Container> 
      ) : (
        <Box sx={{ backgroundColor: '#182c44', display: 'flex', 
          flexDirection: 'column', paddingY: 2}}>
          <Container sx={{marginTop: 2, display: 'flex', flexDirection: 'row'}}>
            <Box>
              <img alt='Country flag' src={`/flags/${countryName}.png`} 
                style={{height: '3.5rem', marginBottom: 8}}/>
            </Box>
            <Container sx={{display: 'flex', flexDirection: 'column'}}>
              <Typography sx={{color: 'white', fontSize: '1rem', fontWeight: '700', 
                paddingY: 0.4, marginLeft: 0.5, marginBottom: 1}}>
                  {nativeName}
              </Typography>
              <Box>
              <Box sx={{display: 'flex'}}>
                <PlaceIcon sx={{color: 'white', fontSize: '1.3rem'}}/>
                <Typography sx={{color: 'white', fontSize: '1rem', fontWeight: '500',
                  marginLeft: 1}}>
                  {cityName}, {countryName}
                </Typography>
              </Box>
              {linkToWebsite && 
                <Box sx={{display: 'flex', marginTop: 1, marginLeft: 0.3}}>
                  <LanguageIcon sx={{color: 'white', fontSize: '1rem', marginTop: 0.5}}/>    
                  <Link href={linkToWebsite} underline="hover"
                    color="primary" sx={{fontWeight: 500,
                      fontSize: '1.1rem', marginLeft: 1,
                      "&:hover": {color: "secondary.main"}}}>
                    Go to website
                  </Link>
                </Box>
              }
              </Box>
              <FollowButton universityId={universityId} isFollowed={isFollowed}
                setIsFollowed={setIsFollowed}/>
            </Container>
          </Container>
        </Box> 
      )}
    </>
  )
}

type FeedPanelProps = {
  universityId: number | string | undefined;
}

const FeedPanel = (props: FeedPanelProps) => {
  const [reviewProps, setReviewsProps] = useState<UniversityReviewProps[]>([])
  const [currentPageNumber, setCurrentPageNumber] = useState<number>(1)
  const [totalPagesCount, setTotalPagesCount] = useState<number>(0)
  const pageSize = 5

  const getReviews = async (pageNumber: number) => {
    if (!props.universityId) {
      return
    }
    const result = await sendGetUniversityReviewsRequest(props.universityId, pageNumber, pageSize)
    if (result.isSuccess) {
      const props: UniversityReviewProps[]  = result.data.map(r => ({
        id: r.id,
        title: r.author.nick,
        subheader: getLocalDate(r.createdAt),
        starRating: r.starRating,
        textContent: r.textContent,
        reactions: r.reactions
      }))
      console.log(props)
      setReviewsProps(props)
    }
  }

  const getTotalPagesCount = async () => {
    if (!props.universityId) {
      return
    }
    const result = await sendGetReviewsCountRequest(props.universityId)
    if (result.isSuccess) {
      setTotalPagesCount(Math.ceil(result.data.count / pageSize))
    }
  }

  useEffect(() => {
    getTotalPagesCount()
  }, [])

  useEffect(() => {
    getReviews(currentPageNumber-1)
  }, [currentPageNumber])

  return (
    <Box sx={{paddingBottom: 4, display: 'flex', flexDirection: 'column', 
      justifyContent: 'space-between', minHeight: '95vh'}}>
      <Box>
        <Typography sx={{fontSize: {xs: '1.3rem', lg: '1.7rem'}, fontWeight: 600 , 
          paddingY: 2.5, paddingLeft: {xs: 2, lg: 4}}}>
          University reviews
        </Typography>
        {reviewProps.length != 0 ? 
          (
            <>
              <Container sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 3}}>
                {reviewProps.map(rp => (<UniversityReview {...rp} key={rp.id}/>))}
              </Container>
            </>
          ) : (
            <NoContent title={'No reviews yet'} 
              subheader={'There are no reviews about this university so far'}/>
          )}        
      </Box>
      {(totalPagesCount > 1) &&
        <Container sx={{display: 'flex', justifyContent: 'center', marginTop: 3}}>
          <Pagination count={totalPagesCount} showFirstButton showLastButton
            onChange={(_, value) => setCurrentPageNumber(value)}/>
        </Container>
      }
    </Box>
  )
}

const UniversityProfilePage = () => {
  const theme = useTheme();
  const isLgScreen = useMediaQuery(theme.breakpoints.up('lg'));
  const { universityId } = useParams()
  const signedInUserId: string | null = localStorage.getItem('userId')

  return (
    <Grid container minHeight='100vh' sx={{backgroundColor: '#eeececff'}}>
      <Grid size={{xs: 12, lg: 3}}>
        {!isLgScreen && <Navbar/>}
        <UniversityDataPanel userId={signedInUserId} universityId={universityId}/>
        {!isLgScreen && <FeedPanel universityId={universityId}/>}
      </Grid>
      <Grid size={{xs: 0, lg: 9}}>
        <Box sx={{minHeight: '100%'}}>
          {isLgScreen && 
            <>
              <Navbar/>
              <FeedPanel universityId={universityId}/>
            </>          
          }
        </Box>
      </Grid>
    </Grid>
  )
}

export default UniversityProfilePage
