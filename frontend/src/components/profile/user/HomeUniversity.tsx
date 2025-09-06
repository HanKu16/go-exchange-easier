import locationPin from "../../../assets/profile/user/location-pin.png"

const HomeUniversity = () => {
  return (
    <div className="xl:h-1/8 h-1/3 w-full flex xl:!pt-2 items-center !pr-3">
      <img src={ locationPin } className="h-3/4 aspect-1/1" />
      <h2 className="font-semibold xl:text-[1.3vw] lg:text-[2vw] text-[3vw] !pl-3">
        Università di Bologna, University of Bologna
      </h2>
    </div>
  )
}

export default HomeUniversity