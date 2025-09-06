import { useNavigate } from "react-router-dom"
import logOutIconImage  from "../../../assets/global/navbar/log-out-icon.png"
import { removeJwtToken } from "../../../utils/auth"
import { useIsLogOutButtonClickedContext } from "./IsLogOutButtonClickedContext"

const LogOut = () => {
  const { setIsLogOutButtonClicked } = useIsLogOutButtonClickedContext()
  const navigate = useNavigate()
  const xlMinDeviceWidth = 1280

  const onLogOutClick = () => {
    if (window.innerWidth > xlMinDeviceWidth) {
      setIsLogOutButtonClicked(true)
    } else {
      removeJwtToken()
      navigate("/login")
    }
  }

  return (
    <div className="h-full w-1/5 flex justify-center items-center">
      <button className="w-13/20 aspect-square" onClick={ onLogOutClick }>
        <img src={ logOutIconImage } alt="Log out image" className=" 
          hover:brightness-105 transition duration-125 ease-in
          active:scale-95 active:shadow-md active:brightness-80"/>
      </button>
    </div>
  )
}

export default LogOut