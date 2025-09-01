import { useState } from "react"
import FormInput from "./FormInput"

const Login = () => {
  const [login, setLogin] = useState("")
  const [password, setPassword] = useState("")

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
            <p className="text-[0.7vw] font-semibold !mt-1.5 !p-1">
              Forgot password?
            </p>
          </div>
          <div className="h-2/10 flex flex-col items-center">
            <button className="bg-ocean-blue w-19/20 rounded-xl shadow-lg text-white !py-2 !mb-2
              transition-colors duration-250 hover:bg-ocean-blue-dark
              ease-in-out active:scale-99 active:shadow-sm">
              Sign In
            </button>
            <p className="text-[0.7vw]">
              Don't have an account?
              <span className="text-ocean-blue"> Sign up for free!</span>
            </p>
          </div>
        </div>
        <div className="h-1/5 w-full flex items-center justify-center">
          <p className="text-amber-400 !pb-10 text-[0.9vw]">
            Learn more about us here!
          </p>
        </div>
      </div>
    </div>
  )
}

export default Login
