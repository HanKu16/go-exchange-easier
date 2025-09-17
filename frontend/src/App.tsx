import { CssBaseline } from "@mui/material"
import LoginPage from "./pages/LoginPage"
import { BrowserRouter, Routes, Route } from 'react-router-dom'

export const App = () => {
  return (
    <BrowserRouter>
    <CssBaseline/>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
