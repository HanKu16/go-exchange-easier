import { Link } from "react-router-dom"
import type { NavigationLinkProps } from "../../../props/global/navbar/NavigationLinkProps"

const NavigationLink = (props: NavigationLinkProps) => {
  return (
    <>
      <Link to={ props.navigateTo } className="w-1/3">
        <div className="h-full w-full flex justify-center items-center">
          <img src={ props.iconImage } alt="Go to profile icon image" className="w-7/10
            aspect-square hover:brightness-110 transition duration-125 ease-in"/>
        </div>
      </Link>
    </>
  )
}

export default NavigationLink