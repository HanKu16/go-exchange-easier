import logOutIconImage  from "../../../assets/global/navbar/log-out-icon.png"
import type { LogOutProps } from "../../../props/global/navbar/LogOutProps"

const LogOut = (props: LogOutProps) => {
  return (
    <div className="h-full w-1/5 flex justify-center items-center">
      <button className="h-3/5 aspect-1/1" onClick={ props.onClick }>
        <img src={ logOutIconImage } alt="Log out image" className=" 
          hover:brightness-105 transition duration-125 ease-in
          active:scale-95 active:shadow-md active:brightness-80"/>
      </button>
    </div>
  )
}

export default LogOut