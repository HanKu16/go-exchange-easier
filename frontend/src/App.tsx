import { Routes, Route } from "react-router-dom"
import LoginPage from "./components/LoginPage"
import RegistrationPage from "./components/registration/RegistrationPage"
import NotImplementedYet from "./components/NotImplementedYet"

const App = () => {
  return (
    <Routes>
      <Route path="login" element={ <LoginPage/> }/>
      <Route path="register" element={ <RegistrationPage/> }/>
      <Route path="profile" element={ <NotImplementedYet/> }/>
      <Route path="learn-more" element={ <NotImplementedYet/> }/>
      <Route path="password-recovery" element={ <NotImplementedYet/> }/>
      <Route path="terms-and-conditions" element={ <NotImplementedYet/> }/>
      <Route path="welcome" element={ <NotImplementedYet/> }/>
    </Routes>
  )
}

export default App