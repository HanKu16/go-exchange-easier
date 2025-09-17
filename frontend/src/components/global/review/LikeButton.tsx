import likeImage from "../../../assets/global/review/like-image.png"
import whiteLikeImage from "../../../assets/global/review/white-like-image.png" 
import { useState } from "react"
import type { LikeButtonProps } from "../../../props/global/review/LikeButtonProps"

const LikeButton = (props: LikeButtonProps) => {
  const [isHovered, setIsHovered] = useState<boolean>(false)

  return (
    <div className="!pr-2">
      <button className="flex h-full items-center rounded-md !px-2 !py-1 
        text-green-700 xl:border-2 border-green-700 border-1 xl:text-[2.2vh] text-[1.2vh]
        hover:text-white hover:bg-green-700 transition duration-150
        lg:text-[2vh]"
        onMouseEnter={ () => setIsHovered(true) }
        onMouseLeave={ () => setIsHovered(false) }>
        <img src={ isHovered ? whiteLikeImage : likeImage} className="h-full"/>
        <span className={"xl:!pb-1 font-semibold !pl-1 !pr-1"}>
          { props.count }
        </span>
      </button>
    </div>
  )
}

export default LikeButton