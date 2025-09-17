import Header from "./Header"
import LikeButton from "./LikeButton"
import DislikeButton from "./DislikeButton"
import type { UniversityReviewProps } from "../../../props/global/review/UniversityReviewProps"
import type { HeaderProps } from "../../../props/global/review/HeaderProps"
import type { LikeButtonProps } from "../../../props/global/review/LikeButtonProps"
import type { DislikeButtonProps } from "../../../props/global/review/DislikeButtonProps"
import type { ReactNode } from "react"

const UniversityReview = (props: UniversityReviewProps) => {
  const headerProps: HeaderProps = {
    author: props.author,
    university: props.university,
    starRating: props.starRating,
    createdAt: props.createdAt
  }
  const buttons: ReactNode[] = [] 

  const likeReaction = props.reactions.find(r => r.name === "like");
  if (likeReaction != undefined) {
    const likeButtonProps: LikeButtonProps =  {
      count: likeReaction.count,
      reaction: {
        typeId: likeReaction.typeId,
        name: "like"
      },
      universityReviewId: props.id,
      isSet: likeReaction.isSet
    };
    buttons.push((<LikeButton {...likeButtonProps}/>))
  }
  const dislikeReaction = props.reactions.find(r => r.name === "dislike");
  if (dislikeReaction != undefined) {
    const dislikeButtonProps: DislikeButtonProps =  {
      count: dislikeReaction.count,
      reaction: {
        typeId: dislikeReaction.typeId,
        name: "dislike"
      },
      universityReviewId: props.id,
      isSet: dislikeReaction.isSet
    };
    buttons.push((<DislikeButton {...dislikeButtonProps}/>))
  }

  return (
    <div className="w-full bg-white !mb-5 rounded-2xl shadow-lg flex flex-col xl:!px-7 !px-3 !py-4
      hover:brightness-97 hover:shadow-xl transition duration-150 ease-in">
      <Header {...headerProps}/>
      <div className="xl:!px-1 xl:!pb-4 !pb-2 !py-4">
        <p className="text-[1.2vh] lg:text-[2.2vh]">
          { props.textContent }
        </p>
      </div>
      <div className="flex xl:h-[6vh] h-[4vh] !pb-3">
        { buttons }
      </div>
    </div>
  )
}

export default UniversityReview