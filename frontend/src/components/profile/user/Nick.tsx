import flag from "../../../assets/profile/user/flag.png"

const Nick = () => {
  return (
    <div className="h-full w-full xl:w-2/3 flex items-center">
      {/* <h1 className="text-lg 
        xl:text-[2.5vw] font-semibold">
          Kentucky Tusk
      </h1> */}
      <span className="text-[5vw]
        lg:text-[3.5vw]
        xl:text-[2.5vw] font-semibold">Kentucky Tusk</span>
      <img src={ flag } className="h-[4vw] xl:h-[2vw] aspect-3/2 lg:!ml-3 !ml-2 !mb-1 xl:!mb-3" />
    </div>
  )
}

export default Nick