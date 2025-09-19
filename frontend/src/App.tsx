import { CssBaseline } from '@mui/material'
import LoginPage from './pages/LoginPage'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import RegistrationPage from './pages/RegistrationPage'
import UserProfilePage from './pages/UserProfilePage'

export const App = () => {
  return (
    <BrowserRouter>
    <CssBaseline/>
      <Routes>
        <Route path='/login' element={<LoginPage/>}/>
        <Route path='/register' element={<RegistrationPage/>}/>
        <Route path='/user/:userId/profile' element={<UserProfilePage/>}/>
      </Routes>
    </BrowserRouter>
  )
}

export default App
