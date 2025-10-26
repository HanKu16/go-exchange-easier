import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { getSignedInUserId, getSignedInUserJwtToken } from '../utils/user'
import { Outlet } from 'react-router-dom'
import LoadingPage from '../pages/LoadingPage'

const RequireAuth = () => {
    const navigate = useNavigate();
    const [isAuthChecked, setIsAuthChecked] = useState(false)

    useEffect(() => {
      try {
        getSignedInUserId()
        getSignedInUserJwtToken()
      } catch (error) {
        navigate('/login')
        return
      }
      setIsAuthChecked(true)
    }, [navigate])

    if (!isAuthChecked) {
        return <LoadingPage backgroundColor='#eeececff' 
          circularProgressColor='#182c44' text=''/>
    }
    return <Outlet/>
};

export default RequireAuth