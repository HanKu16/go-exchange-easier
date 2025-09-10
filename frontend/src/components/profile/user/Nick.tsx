import { useUserProfileContext } from "./UserProfileContext"

const Nick = () => {
  const { user } = useUserProfileContext()

  return (
    <div className="h-full w-full xl:w-2/3 flex items-center">
      <span className="text-[5vw]
        lg:text-[3.5vw]
        xl:text-[2.5vw] font-semibold">{ user?.nick }</span>
        { user && user.countryOfOrigin ? (
          <img src={`/flags/${ user.countryOfOrigin.name }.png`} 
            className="h-[4vw] xl:h-[2vw] aspect-3/2 lg:!ml-3 
            !ml-2 !mb-1 xl:!mb-3" />
        ) : (<></>)
        }
    </div>
  )
}

export default Nick