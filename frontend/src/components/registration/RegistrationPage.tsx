import { useEffect, useState } from "react"
import type { FormInputProps } from "../../props/registration/FormInputProps"
import FormField from "./FormField"
import { Link } from "react-router-dom";
import captionImage from "../../assets/registration/caption.png"
import registrationPageTextImage from "../../assets/registration/text.png"
import earthImage from "../../assets/registration/earth.png"

const RegistrationPage = () => {
  const [login, setLogin] = useState<string>("")
  const [password, setPassword] = useState<string>("")
  const [mail, setMail] = useState<string>("")
  const [nick, setNick] = useState<string>("")
  const [isAgreementChecked, setIsAgreementCheck] = useState<boolean>(false)

  const loginInputProps: FormInputProps  = {
    label: "Login",
    id: "login-input",
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
    id: "password-input",
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
    id: "mail-input",
    value: mail,
    isObligatory: false,
    suggestions: [],
    onChange: (event => setMail(event.target.value))
  }
  const mailDescriptionProps = {
    text: "By giving email you let us notify you about new messages"
  }

  const nickInputProps: FormInputProps = {
    label: "Nick",
    id: "nick-input",
    value: nick,
    isObligatory: false,
    suggestions: ["should have between 3 to 15 signs", "only letters and numbers"],
    onChange: (event => setNick(event.target.value))
  }
  const nickDescriptionProps = {
    text: "Your nick will be shown on your profile"
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
          <img src={ earthImage } className="scale-60" />
        </div>
        <div className="h-5/15 flex justify-center items-start">
          <img src={ registrationPageTextImage } className="scale-75 !pt-9" />
        </div>
      </div>
      <div className="bg-dirty-white h-scree w-1/2">
        <div className="w-full h-1/12 flex justify-center items-center !pt-3 text-[1.7vw]">
          <h1>REGISTRATION FORM</h1>
        </div>
        <div className="w-full h-7/10 flex flex-col justify-around">
          <FormField inputProps={ loginInputProps } 
            descriptionProps={ loginDescriptionProps }/>
          <FormField inputProps={ passwordInputProps } 
            descriptionProps={ passwordDescriptionProps }/>
          <FormField inputProps={ mailInputProps } 
            descriptionProps={ mailDescriptionProps }/>
          <FormField inputProps={ nickInputProps } 
            descriptionProps={ nickDescriptionProps }/>
        </div>
        <div className="w-full h-2/10 flex justify-start items-center">
          <div className="w-6/10 h-full flex flex-col justify-center items-center !pb-3">
            <button className="bg-lapis-lazull text-sunny-yellow font-medium rounded-xl !py-2 w-7/10
              shadow-lg text-[1vw] !mb-3 transition-colors duration-250 hover:brightness-90
              ease-in-out active:scale-99 active:shadow-lg">
              Sing Up
            </button>
            <label htmlFor="agree-checkbox" className="text-[1vw]">
              <input
                type="checkbox"
                id="agree-checkbox"
                checked={ isAgreementChecked }
                onChange={ event => setIsAgreementCheck(event.target.checked) }
                className="accent-lapis-lazull !mr-2 w-3 h-3"
              />
              I read and agree to&nbsp;
              <Link to="/terms-and-conditions" className="font-bold 
                hover:shadow-md duration-150 ease-in">
                  Terms and Conditions
              </Link>
            </label>
          </div>
        </div>
      </div>
    </div>
  )
}

export default RegistrationPage
