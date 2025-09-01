import type { LoginRequest } from "../dto/auth/LoginRequest";
import type { LoginResult } from "../utils/auth";
import { useEffect, useState } from "react"
import { useNavigate, Link } from "react-router-dom";
import { sendLoginRequest } from "../utils/auth";
import FormInput from "./FormInput"

const LoginPage = () => {
  const navigate = useNavigate()
  const [login, setLogin] = useState<string>("")
  const [password, setPassword] = useState<string>("")
  const [isLoginButtonClicked, setIsButtonClicked] = useState<boolean>(false)
  const [loginFailedMessage, setLoginFailedMessage] = useState<string>("")
  
  const handleLogin = async () => {
    const body: LoginRequest = {
      login,
      password
    }
    const result: LoginResult = await sendLoginRequest(body)
    if (result.isSuccess) {
        localStorage.setItem("jwtToken", result.data.accessToken)
        localStorage.setItem("tokenType", result.data.tokenType)
        navigate("/profile")
    } else {
        setLogin(() => "")
        setPassword(() => "")
        setIsButtonClicked(() => false)
        setLoginFailedMessage("Invalid credentials")
    }
  }

  useEffect(() => {
    document.title = "Go Exchange Easier"
  }, [])

  useEffect(() => {
    if (isLoginButtonClicked) {
      handleLogin()
    }
  }, [isLoginButtonClicked])

  return (
    <div className="bg-dark-blue h-screen flex justify-center items-center">
      <div className="bg-white rounded-xl w-3/10 h-7/10 flex flex-col items-center overflow-hidden">
        <div className="h-1/5 w-full flex flex-col items-center justify-center">
          <h1 className="text-neutral-950 text-[2.5vw] font-medium">
            WELCOME BACK
          </h1>
          <p className="text-zinc-400 text-[0.8vw] mt-10">
            We are happy to see you again!
          </p>
        </div>
        <div className="h-3/5 w-3/5 bg-white flex flex-col justify-around">
          <div className="h-2/10">
            <FormInput label="Login" id="login-input" value={ login } 
              onChange={ event => setLogin(event.target.value)}
              placeholder="Enter your login"/>
          </div>
          <div className="h-3/10 flex flex-col justify-center">
            <FormInput label="Password" id="password-input" value={ password } 
              onChange={ event => setPassword(event.target.value) }
              placeholder="Enter your password"/>
            <Link to="/password-recovery" className="text-[0.7vw] font-semibold 
              !mt-1.5 !p-1 hover:text-shadow-lg hover:font-bold duration-100 ease-in">
              Forgot password?
            </Link>
          </div>
          <div className="h-2/10 flex flex-col items-center">
            <button className="bg-ocean-blue w-19/20 rounded-xl shadow-lg text-white !py-2 !mb-2
              transition-colors duration-250 hover:bg-ocean-blue-dark
              ease-in-out active:scale-99 active:shadow-sm"
              onClick={() => setIsButtonClicked(true)}>
              Sign In
            </button>
            <p className="text-[0.7vw]">
              Don't have an account?
              <Link to="/register" className="text-ocean-blue hover:font-semibold 
                duration-150 ease-in"> 
                Sign up for free!
              </Link>
            </p>
          </div>
        </div>
        <div className="h-1/5 w-full flex flex-col items-center justify-around">
          <p className="text-red-600 text-[1vw] font-semibold text-shadow-sm">
            { loginFailedMessage }
          </p>
          <Link to="/learn-more" className="text-amber-400 !pb-10 text-[0.9vw] 
            hover:text-amber-600 duration-150 ease-in">
            Learn more about us here!
          </Link>
        </div>
      </div>
    </div>
  )
}

export default LoginPage
