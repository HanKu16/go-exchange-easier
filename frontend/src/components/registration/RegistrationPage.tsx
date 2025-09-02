import { useEffect, useState } from "react"
import type { FormInputProps } from "../../props/registration/FormInputProps"
import FormField from "./FormField"
import { Link, useNavigate } from "react-router-dom";
import captionImage from "../../assets/registration/caption.png"
import registrationPageTextImage from "../../assets/registration/text.png"
import earthImage from "../../assets/registration/earth.png"
import { sendUserRegistrationRequest } from "../../utils/user";
import type { UserRegistrationResult } from "../../utils/user";
import type { UserRegistrationRequest } from "../../dto/user/UserRegistrationRequest";
import { SuggestionsColorsContext } from "./SuggestionsColorsContext";
import type { ApiErrorResponseCode } from "../../dto/error/ApiErrorResponseCode";

const RegistrationPage = () => {
  const navigate = useNavigate()
  const [login, setLogin] = useState<string>("")
  const [password, setPassword] = useState<string>("")
  const [mail, setMail] = useState<string>("")
  const [nick, setNick] = useState<string>("")
  const [isAgreementChecked, setIsAgreementCheck] = useState<boolean>(false)
  const [errorMessage, setErrorMessage] = useState<string>("")
  const [suggestionsColors, setSuggestionsColors] = useState({
    login: "text-zinc-500",
    password: "text-zinc-500",
    mail: "text-zinc-500",
    nick: "text-zinc-500"
  })

  const loginInputProps: FormInputProps  = {
    label: "Login",
    id: "login",
    value: login,
    isObligatory: true,
    suggestions: ["should have between 6 to 20 signs", "only letters and numbers"],
    onChange: (event => setLogin(event.target.value))
  }
  const loginDescriptionProps = {
    text: "It will be your identifier in our service used for signing up"
  }

  const passwordInputProps: FormInputProps  = {
    label: "Password",
    id: "password",
    value: password,
    isObligatory: true,
    suggestions: ["should have between 8 to 20 signs", "only letters and numbers"],
    onChange: (event => setPassword(event.target.value))
  }
  const passwordDescriptionProps = {
    text: "Will be used to sign in to our service, should be hard to guess"
  }

  const mailInputProps: FormInputProps = {
    label: "Mail",
    id: "mail",
    value: mail,
    isObligatory: false,
    suggestions: ["must be valid mail address"],
    onChange: (event => setMail(event.target.value))
  }
  const mailDescriptionProps = {
    text: "By giving email you let us notify you about new messages"
  }

  const nickInputProps: FormInputProps = {
    label: "Nick",
    id: "nick",
    value: nick,
    isObligatory: true,
    suggestions: ["should have between 3 to 15 signs", "only letters and numbers"],
    onChange: (event => setNick(event.target.value))
  }
  const nickDescriptionProps = {
    text: "Your nick will be shown on your profile"
  }

  const handleRegistration = async () => {
    const body: UserRegistrationRequest = {
      login: login,
      password: password,
      mail: mail,
      nick: nick
    }
    const result: UserRegistrationResult = await sendUserRegistrationRequest(body)

    if (result.isSuccess) {
      navigate("/welcome")
    } else {
      const errorFieldNames: string[] = result.error.fieldErrors.map(e => e.field)
      const globalErrorsCodes: ApiErrorResponseCode[] = 
        result.error.globalErrors.map(e => e.code)
      setSuggestionsColors({
        login: errorFieldNames.includes("login") ? "text-red-600" : "text-zinc-500",
        password: errorFieldNames.includes("password") ? "text-red-600" : "text-zinc-500",
        mail: errorFieldNames.includes("mail") ? "text-red-600" : "text-zinc-500",
        nick: errorFieldNames.includes("nick") ? "text-red-600" : "text-zinc-500"
      })
      if (globalErrorsCodes.includes("LoginAlreadyTaken")) {
        setErrorMessage("Login is already taken")
      } else if (globalErrorsCodes.includes("MailAlreadyTaken")) {
        setErrorMessage("Account associated with given name already exists")
      } else if (globalErrorsCodes.includes("InternalError") || 
          result.error.status === "INTERNAL_SERVER_ERROR") {
        setErrorMessage("An unexpected error occured")
      }
    }
  }

  const handleClickOnRegistrationButton = () => {
    if (isAgreementChecked) {
      setErrorMessage("")  
      handleRegistration()      
    } else {
      setErrorMessage("You have to accept Terms and Conditions to sign up")
    }
  }

  useEffect(() => {
    document.title = "Go Exchange Easier"
  }, [])

  return (
    <div className="h-screen flex">
      <div className="bg-dark-blue h-screen w-1/2">
        <div className="h-4/15 flex justify-center items-end scale-85">
          <img src={ captionImage } alt="Go Exchange Easier caption" />
        </div>
        <div className="h-6/15 flex justify-center items-center ">
          <img src={ earthImage } alt="Yellow Earth image" className="scale-60" />
        </div>
        <div className="h-5/15 flex justify-center items-start">
          <img src={ registrationPageTextImage } alt="Decoration text" 
            className="scale-75 !pt-9" />
        </div>
      </div>
      <div className="bg-dirty-white h-scree w-1/2">
        <div className="w-full h-1/12 flex justify-center items-center !pt-3 text-[1.7vw]">
          <h1>REGISTRATION FORM</h1>
        </div>
        <div className="w-full h-7/10 flex flex-col justify-around">
          <SuggestionsColorsContext.Provider 
            value={suggestionsColors}>
            <FormField inputProps={ loginInputProps } 
              descriptionProps={ loginDescriptionProps }/>
            <FormField inputProps={ passwordInputProps } 
              descriptionProps={ passwordDescriptionProps }/>
            <FormField inputProps={ nickInputProps } 
              descriptionProps={ nickDescriptionProps }/>
            <FormField inputProps={ mailInputProps } 
              descriptionProps={ mailDescriptionProps }/>
          </SuggestionsColorsContext.Provider>
        </div>
        <div className="w-full h-2/10 flex justify-start items-center">
          <div className="w-6/10 h-full flex flex-col justify-center items-center !pb-3">
            <button className="bg-lapis-lazull text-sunny-yellow font-medium rounded-xl !py-2 w-7/10
              shadow-lg text-[1vw] !mb-3 transition-colors duration-250 hover:brightness-90
              ease-in-out active:scale-99 active:shadow-lg" 
              onClick={ handleClickOnRegistrationButton }>
              Sing Up
            </button>
            <label htmlFor="agree-checkbox" className="text-[1vw]">
              <input
                type="checkbox"
                id="agree-checkbox"
                checked={ isAgreementChecked }
                onChange={ () => setIsAgreementCheck(prevState => !prevState) }
                className="accent-lapis-lazull !mr-2 w-3 h-3"
              />
              I read and agree to&nbsp;
              <Link to="/terms-and-conditions" className="font-bold 
                hover:shadow-md duration-150 ease-in">
                  Terms and Conditions
              </Link>
            </label>
            <p className="text-red-600 text-[1vw] font-semibold text-shadow-sm !mt-1">
              { errorMessage }
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default RegistrationPage
