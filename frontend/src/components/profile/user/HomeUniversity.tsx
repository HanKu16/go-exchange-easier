import locationPin from "../../../assets/profile/user/location-pin.png"
import { useUserProfileContext } from "./UserProfileContext"

const HomeUniversity = () => {
  const { user } = useUserProfileContext()
  const universityLabel = user && user.homeUniversity ?
    user.homeUniversity.nativeName + 
    (user.homeUniversity.englishName ? `, ${user.homeUniversity.englishName}` : "") :
    "no info about home university"

  return (
    <div className="xl:h-1/8 h-1/3 w-full flex xl:!pt-2 items-center !pr-3">
      <img src={ locationPin } className="h-3/4 aspect-1/1" />
      <h2 className="font-semibold xl:text-[1.3vw] lg:text-[2vw] text-[3vw] !pl-3"> 
        { universityLabel }
      </h2>
    </div>
  )
}

export default HomeUniversity
