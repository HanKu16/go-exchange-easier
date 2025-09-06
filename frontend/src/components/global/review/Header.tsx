import starRating from "../../../assets/global/review/star-rating.png"

const Header = () => {
  return (
    <div className="flex justify-between xl:h-[7vh] h-[3vh] w-full">
      <div className="flex items-center h-full ">
        <span className="text-dark-blue font-semibold xl:text-[2.3vh] text-[1.1vh]">
          Università di Bologna, University of Bologna
        </span>
        <div className="flex items-center xl:!pl-3 !pl-1 h-full">
          <img src={ starRating } className="xl:h-2/5 !pr-1 h-1/2 aspect-3/2" />
          <span className="font-bold text-sunny-yellow xl:text-[2.3vh] text-[1.1vh]">
            5.0
          </span>
        </div>
      </div>
      <div className="flex items-center justify-end !pr-3">
        <span className="text-dark-blue font-semibold xl:text-[2.3vh] text-[1.1vh]">
          2.11.2023
        </span>
      </div>
    </div>
  )
}

export default Header