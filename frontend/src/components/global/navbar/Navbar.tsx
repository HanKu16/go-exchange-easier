import { useState } from "react"
import SearchBar from "./SearchBar";
import chatIconImage  from "../../../assets/global/navbar/chat-icon.png"
import homeIconImage  from "../../../assets/global/navbar/home-icon.png"
import profileIconImage  from "../../../assets/global/navbar/profile-icon.png"
import NavigationLink from "./NavigationLink";
import { IsLogOutButtonClickedContextProvider } from "./IsLogOutButtonClickedContext.tsx"
import LogOutAndLogo from "./LogOutAndLogo.tsx";
import type { UniversityNameSearchLanguage } from "../../../types/UniversityNameSearchLanguage.tsx";

const Navbar = () => {
  const [searchLanguage, setSearchLanguage] = useState<UniversityNameSearchLanguage>("english")

  const changeSearchLanguage = () => {
    setSearchLanguage(prevState => prevState === "english" ? 
      "native" : "english")
  }

  return (
    <nav className="bg-dark-blue w-full h-[8vh] sm:h-[12vh] flex">
      <IsLogOutButtonClickedContextProvider>
        <LogOutAndLogo/>
      </IsLogOutButtonClickedContextProvider>
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