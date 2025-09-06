import Navbar from "../../global/navbar/Navbar"
import Header from "./Header";
import Feed from "./Feed";

const UserProfile = () => {
  return (
    <div className="min-h-screen bg-dark-blue">
        <Navbar/>
        <div className="w-full h-full flex flex-col items-center 
          sm:!pt-4">
          <Header/>
          <Feed/>
        </div>
    </div>
  )
}

export default UserProfile;

