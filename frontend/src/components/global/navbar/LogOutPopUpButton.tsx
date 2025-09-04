import type { LogOutPopUpButtonProps } from "../../../props/global/navbar/LogOutPopUpButtonProps"

const LogOutPopUpButton = (props: LogOutPopUpButtonProps) => {
  return (
    <>
      <button className="bg-sunny-yellow h-3/5 !px-6 rounded-4xl font-bold
        hover:brightness-115 transition duration-125 ease-in
        active:scale-98 active:shadow-md active:brightness-80"
        onClick={ props.onClick }>
          { props.buttonText }
      </button>
    </>
  )
}

export default LogOutPopUpButton