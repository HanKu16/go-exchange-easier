import LogOutPopUpButton from "./LogOutPopUpButton"
import { useIsLogOutButtonClickedContext } from "./IsLogOutButtonClickedContext";
import { removeJwtToken } from "../../../utils/auth.tsx"
import { useNavigate } from "react-router-dom";

const LogOutPopUp = () => {
  const navigate = useNavigate()
  const { setIsLogOutButtonClicked } = useIsLogOutButtonClickedContext();

  const onYesButtonClick = () => {
    removeJwtToken()
    navigate("/login")
  }
 
  const onNoButtonClick = () => {
    setIsLogOutButtonClicked(false)
  }

  return (
    <div className="w-full h-full flex flex-col justify-around items-center text-dark-blue !py-2">
      <div className="flex justify-center items-center h-1/2 justify-center items-center">
        <p className="text-sunny-yellow font-semibold text-[1vw]">
          Do you want to sign out?
        </p>
      </div>
      <div className="w-2/5 h-full flex justify-around items-center h-1/2">
        <LogOutPopUpButton buttonText="Yes" onClick={ onYesButtonClick }/>
        <LogOutPopUpButton buttonText="No" onClick={ onNoButtonClick }/>
      </div>
    </div>
  )
}

export default LogOutPopUp