import { Link } from "react-router-dom"
import type { NavigationLinkProps } from "../../../props/global/navbar/NavigationLinkProps"

const NavigationLink = (props: NavigationLinkProps) => {
  return (
    <>
      <Link to={ props.navigateTo }>
        <div className="h-full aspect-1/1 flex justify-center items-center">
          <img src={ props.iconImage } alt="Go to profile icon image" className="h-4/5 
            aspect-1/1 hover:brightness-110 transition duration-125 ease-in"/>
        </div>
      </Link>
    </>
  )
}

export default NavigationLink