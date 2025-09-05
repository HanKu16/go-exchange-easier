import type { HeaderButtonProps } from "../../props/profile/user/HeaderButtonProps"

const HeaderButton = (props: HeaderButtonProps) => {
  return (
      <button className="bg-dark-blue h-3/5 w-1/2 text-sunny-yellow !px-4 !py-1 
          rounded-3xl flex items-center !mr-4">
        <img src={ props.icon } alt="" className="w-[2vw] aspect-1/1"/>
        <span className="font-semibold text-[1vw] !pl-1">
          {props.buttonText}
        </span>
      </button>
  )
}

export default HeaderButton