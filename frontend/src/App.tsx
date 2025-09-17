import { Routes, Route } from "react-router-dom"
import LoginPage from "./components/login/LoginPage"
import RegistrationPage from "./components/registration/RegistrationPage"
import NotImplementedYet from "./components/NotImplementedYet"
import UserProfile from "./components/profile/user/UserProfile"

const App = () => {
  return (
    <Routes>
      <Route path="login" element={ <LoginPage/> }/>
      <Route path="register" element={ <RegistrationPage/> }/>
      <Route path="user-profile/:userId" element={ <UserProfile/> }/>
      <Route path="user-profile/:userId/not-found" element={ <NotImplementedYet/> }/>
      <Route path="learn-more" element={ <NotImplementedYet/> }/>
      <Route path="password-recovery" element={ <NotImplementedYet/> }/>
      <Route path="terms-and-conditions" element={ <NotImplementedYet/> }/>
    </Routes>
  )
}

export default App