import { useEffect, useState } from "react"
import UniversityReview from "../../global/review/UniversityReview"
import { useUserProfileContext } from "./UserProfileContext"
import { sendGetUserReviewsRequest } from "../../../utils/user-profile"
import { useParams } from "react-router-dom"
import type { UserProfilePageParams } from "../../../types/UserProfilePageParams"
import type { GetUniversityReviewResponse } from "../../../dto/review/GetUniversityReviewResponse"
import type { UniversityReviewProps } from "../../../props/global/review/UniversityReviewProps"

const Feed = () => {
  const { user } = useUserProfileContext()
  const { userId } = useParams<UserProfilePageParams>()
  const [reviewsProps, setReviewsProps] = useState<UniversityReviewProps[]>([])

  const fetchUserReviews = async (userId: number) => {
    const result = await sendGetUserReviewsRequest(userId);
    if (result.isSuccess) {
      const revies: GetUniversityReviewResponse[] = result.data
      const universityReviewsProps: UniversityReviewProps[] = revies.map(r => ({
        id: r.id,
        author: {
          id: r.author.id,
          nick: r.author.nick,
        },
        university: {
          id: r.university.id,
          englishName: r.university.englishName,
          nativeName: r.university.nativeName,
        },
        starRating: r.starRating,
        textContent: r.textContent,
        createdAt: r.createdAt,
        reactions: r.reactions.map(re => ({
          typeId: re.typeId,
          name: re.name,
          count: re.count,
          isSet: re.isSet,
        }))
      }))
      setReviewsProps(universityReviewsProps)
    }
  }

  useEffect(() => {
    if (userId) {
      fetchUserReviews(parseInt(userId))
    }
  }, [userId])

  return (
    <div className="flex flex-col bg-dirty-white xl:w-[80vw] h-1/2 brightness-95 xl:!px-15 !px-5 !pt-3 w-[92vw]">
      <h2 className="text-neutral-500 xl:text-[1.4vw] text-[2.5vw] xl:!py-5 !pb-3 font-bold">
        University reviews written by { user?.nick }
      </h2>
      <div className="flex flex-col">
        { reviewsProps.map(p => (<UniversityReview {...p}/>)) }
      </div>
    </div>
  )
}

export default Feed