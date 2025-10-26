import { CssBaseline } from '@mui/material'
import LoginPage from './pages/LoginPage'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import RegistrationPage from './pages/RegistrationPage'
import UserProfilePage from './pages/UserProfilePage'
import EditUserPage from './pages/EditUserPage'
import UniversityProfilePage from './pages/UniversityProfilePage'
import { SnackbarProvider } from './context/SnackBarContext'
import RequireAuth from './components/RequireAuth'

export const App = () => {
  return (
    <BrowserRouter>
    <CssBaseline/>
      <SnackbarProvider>
        <Routes>
          <Route path='/login' element={<LoginPage/>}/>
          <Route path='/register' element={<RegistrationPage/>}/>
          <Route element={<RequireAuth />}>
            <Route path='/users/:userId/profile' element={<UserProfilePage/>}/>
            <Route path='/universities/:universityId' element={<UniversityProfilePage/>}/>
            <Route path='/me' element={<EditUserPage/>}/>
          </Route>
        </Routes>
      </SnackbarProvider>
    </BrowserRouter>
  )
}

export default App
