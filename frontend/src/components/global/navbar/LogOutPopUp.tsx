import type { LogOutPopUpProps } from "../../../props/global/navbar/LogOutPopUpProps"
import LogOutPopUpButton from "./LogOutPopUpButton"

const LogOutPopUp = (props: LogOutPopUpProps) => {
  return (
    <div className="w-full h-full flex flex-col justify-around items-center text-dark-blue !py-2">
      <p className="text-sunny-yellow font-semibold">
        Do you want to log out?
      </p>
      <div className="w-2/5 h-full flex justify-around items-center">
        <LogOutPopUpButton buttonText="Yes" onClick={ props.onYes }/>
        <LogOutPopUpButton buttonText="No" onClick={ props.onNo }/>
      </div>
    </div>
  )
}

export default LogOutPopUp