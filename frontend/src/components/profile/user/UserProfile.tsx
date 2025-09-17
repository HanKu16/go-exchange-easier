import Navbar from "../../global/navbar/Navbar"
import Header from "./Header";
import Feed from "./Feed";
import { UserProfileContextProvider } from "./UserProfileContext";
import { useEffect } from "react";

const UserProfile = () => {
  useEffect(() => {
    document.title = "Go Exchange Easier"
  }, [])

  return (
    <div className="min-h-screen bg-dark-blue">
      <UserProfileContextProvider>
        <Navbar/>
        <div className="w-full h-full flex flex-col items-center 
          sm:!pt-4">
          <Header/>
          <Feed/>
        </div>
      </UserProfileContextProvider>
    </div>
  )
}

export default UserProfile;

