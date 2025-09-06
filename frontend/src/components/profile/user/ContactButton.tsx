import { useNavigate } from "react-router-dom"
import messageIcon from "../../../assets/profile/user/message-icon.png"

const ContactButton = () => {
  const navigate = useNavigate()

  return (
    <div className="xl:w-1/2 !pr-1 xl:h-full h-full w-full flex items-center">
      <button className="bg-dark-blue text-sunny-yellow 
          xl:!px-4 xl:!py-2 xl:h-9/10 xl:w-19/20
          !px-4 !py-2 h-3/5 w-4/5 xl:justify-start
          rounded-3xl flex items-center justify-center
          hover:brightness-120 transition duration-125 ease-in
          active:scale-95 active:shadow-md"
          onClick={ () => navigate("/chat") }>
        <img src={ messageIcon } alt="" className="xl:w-[2vw] w-[2.2vw] aspect-1/1"/>
        <span className="font-semibold xl:text-[1vw] text-[1.5vw] !pl-1">
          contact
        </span>
      </button>
    </div>
  )
}

export default ContactButton