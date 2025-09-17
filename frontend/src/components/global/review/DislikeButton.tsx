import dislikeImage from "../../../assets/global/review/dislike-image.png"
import whiteDislikeImage from "../../../assets/global/review/white-dislike-image.png" 
import { useState } from "react"
import type { DislikeButtonProps } from "../../../props/global/review/DislikeButtonProps"

const DislikeButton = (props: DislikeButtonProps) => {
  const [isHovered, setIsHovered] = useState<boolean>(false)

  return (
    <div className="!pr-2">
      <button className="flex h-full items-center rounded-md !px-2 !py-1 
        text-red-500 xl:border-2 border-red-500 border-1 xl:text-[2.2vh] text-[1.2vh]
        hover:text-white hover:bg-red-500 transition duration-150
        lg:text-[2vh]"
        onMouseEnter={ () => setIsHovered(true) }
        onMouseLeave={ () => setIsHovered(false) }>
        <img src={ isHovered ? whiteDislikeImage : dislikeImage} className="h-full"/>
        <span className={"xl:!pb-1 font-semibold !pl-1 !pr-1"}>
          { props.count }
        </span>
      </button>
    </div>
  )
}

export default DislikeButton
