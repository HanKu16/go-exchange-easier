import { useState } from "react"
import LogOut from "./LogOut"
import Logo from "./Logo";
import LogOutPopUp from "./LogOutPopUp";
import { useNavigate } from "react-router-dom";
import SearchBar from "./SearchBar";
import chatIconImage  from "../../../assets/global/navbar/chat-icon.png"
import homeIconImage  from "../../../assets/global/navbar/home-icon.png"
import profileIconImage  from "../../../assets/global/navbar/profile-icon.png"
import NavigationLink from "./NavigationLink";

const Navbar = () => {
  const navigate = useNavigate()
  const [isLogOutButtonClicked, setIsLogOutButtonClicked] = useState<boolean>(false)
  const [searchLanguage, setSearchLanguage] = useState<string>("english")

  const changeSearchLanguage = () => {
    setSearchLanguage(prevState => prevState === "english" ? 
      "native" : "english")
  }

  return (
    <nav className="bg-dark-blue w-full h-[8vh] sm:h-[12vh] flex">
      { isLogOutButtonClicked ? (
          <div className="w-3/9 h-full">
            <LogOutPopUp onYes={ () => navigate("/login") }
              onNo={ () => setIsLogOutButtonClicked(false) }/>
          </div>
        ) : (
          <div className="flex w-3/9 h-full">
            <LogOut onClick={ () => setIsLogOutButtonClicked(true) }/>
            <Logo/>
          </div>
        )
      }
      <div className="flex w-4/9 h-full justify-center !pr-5 !pt-1.5">
        <SearchBar searchLanguage={ searchLanguage } 
          onLanguageChange={ changeSearchLanguage }
          onSearch={ () => {} }/>
      </div>
      <div className="flex w-2/9 f-full justify-around">
        <NavigationLink iconImage={ profileIconImage } navigateTo="/profile"/>
        <NavigationLink iconImage={ chatIconImage } navigateTo="/chat"/>
        <NavigationLink iconImage={ homeIconImage } navigateTo="/home"/>
      </div>
    </nav>
  )
}

export default Navbar