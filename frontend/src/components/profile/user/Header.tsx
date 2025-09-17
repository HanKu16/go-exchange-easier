import Avatar from "./Avatar"
import Nick from "./Nick"
import HomeUniversity from "./HomeUniversity"
import FollowButton from "./FollowButton"
import ContactButton from "./ContactButton"
import { useEffect } from "react"
import { sendGetUserProfileRequest } from "../../../utils/user-profile"
import type { GetUserProfileResponse } from "../../../dto/user/GetUserProfileResponse"
import type { ApiErrorResponse } from "../../../dto/error/ApiErrorResponse"
import { useUserProfileContext } from "./UserProfileContext"
import { useParams } from "react-router-dom"
import type { UserProfilePageParams } from "../../../types/UserProfilePageParams"

// type UserProfileRequestParams = {
//   userId: string;
// }
  
const Header = () => {
  const { user, setUser } = useUserProfileContext()
  const { userId } = useParams<UserProfilePageParams>();

  const fetchUserProfile = async (userId: number) => {
    const result = await sendGetUserProfileRequest(userId)
    if (result.isSuccess) {
      const user: GetUserProfileResponse = result.data
      setUser({
        id: user.userId,
        nick: user.nick,
        description: user.description,
        isFollowed: user.isFollowed,
        homeUniversity: user.homeUniversity ? { 
          id: user.homeUniversity.id, 
          nativeName: user.homeUniversity.nativeName, 
          englishName: user.homeUniversity.englishName 
        } : undefined,
        countryOfOrigin: user.countryOfOrigin ? { 
          id: user.countryOfOrigin.id, 
          name: user.countryOfOrigin.name 
        } : undefined,
        status: user.status ? { 
          id: user.status.id, 
          name: user.status.name 
        } : undefined
      });
    } else {
      const data: ApiErrorResponse = result.error
      console.log(data)
    }
  }

  useEffect(() => {
      if (userId !== undefined) {
        fetchUserProfile(parseInt(userId))
      }
  }, [userId])

  return (
    <div className="bg-sunny-yellow flex flex-col overflow-hidden text-dark-blue
      rounded-tl-4xl rounded-tr-4xl  
      w-[92vw] h-[25vh]
      md:h-[30vh]
      xl:h-[45vh] xl:w-[80vw]">
      <div className="w-full xl:h-full flex h-1/2">
        <Avatar />
        <div className="w-full flex flex-col xl:!pl-8 !pl-6 xl:h-full h-full">
          <div className="h-1/3 xl:h-1/4 w-full flex">
            <Nick/>
            <div className={"hidden xl:h-full xl:w-1/3 flex xl:!pt-6 w-full xl:!mr-2 xl:flex xl:flex-row xl:justify-end"}>
              <ContactButton/>
              <FollowButton/>
            </div>
          </div>
          <HomeUniversity/>
          <div className="xl:hidden flex h-2/5 w-2/5 justify-around">
            <ContactButton/>
            <FollowButton/>
          </div>
          <div className="hidden xl:flex h-5/8 w-full !pr-12 !pl-1 !pt-6 font-medium">
            <p>
              { user?.description }
            </p>
          </div>
        </div>
      </div>
      <div className="xl:hidden text-[2vw] !px-3 !pt-5">
        <p>
          { user?.description }
        </p>
      </div>
    </div>
  )
}

export default Header


