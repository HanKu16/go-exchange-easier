import { Routes, Route } from "react-router-dom"
import LoginPage from "./components/LoginPage"
import NotImplementedYet from "./components/NotImplementedYet"

const App = () => {
  return (
    <Routes>
      <Route path="login" element={ <LoginPage/> }/>
      <Route path="register" element={ <NotImplementedYet/> }/>
      <Route path="profile" element={ <NotImplementedYet/>}/>
      <Route path="learn-more" element={ <NotImplementedYet/>}/>
      <Route path="password-recovery" element={ <NotImplementedYet/>}/>
    </Routes>
  )
}

export default App