import starRating from "../../../assets/global/review/star-rating.png"
import type { HeaderProps } from "../../../props/global/review/HeaderProps"

const Header = (props: HeaderProps) => {
  const nativeUniveristyName = props.university.nativeName
  const englishUniversityName = props.university.englishName
  const universityName = nativeUniveristyName + 
    (englishUniversityName ? `, ${ englishUniversityName }` : "")
  const formattedDate = props.createdAt.slice(0, 10)

  return (
    <div className="flex justify-between items-center !py-[min(5px, 2vw)] w-full">
      <div className="flex flex-1 items-center max-w-[75%]">
        <span className="text-dark-blue font-semibold xl:text-[2.3vh] text-[1.1vh]">
          { universityName }
        </span>
        <img src={ starRating } className="xl:h-2/5 !pr-1 h-1/2 aspect-3/2"/>
        <span className="font-bold text-sunny-yellow xl:text-[2.3vh] text-[1.1vh]">
          { props.starRating }
        </span>
      </div>
      <div>
        <span className="text-dark-blue font-semibold xl:text-[2.3vh] text-[1.1vh]">
          { formattedDate }
        </span>
      </div>
    </div>
  )
}

export default Header
