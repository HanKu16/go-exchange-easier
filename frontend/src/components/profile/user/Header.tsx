import HeaderButton from "../HeaderButton"
import messageIcon from "../../../assets/profile/user/message-icon.png"
import saveIcon from "../../../assets/profile/user/save-icon.png"
import flag from "../../../assets/profile/user/flag.png"
import locationPin from "../../../assets/profile/user/location-pin.png"
import profileExampleImage from "../../../assets/profile/user/profile-example-image.png"
  
const Header = () => {
  return (
    <div className="bg-sunny-yellow w-4/5 min-h-1/2 rounded-tl-4xl rounded-tr-4xl
      flex overflow-hidden text-dark-blue">
        <div className="h-full aspect-1/1 bg-dirty-white brightness-90">
          <img src={ profileExampleImage } className="w-full h-full" />
        </div>
        <div className="h-full w-full flex flex-col !pl-8">
          <div className="h-2/8 w-full flex">
            <div className="h-full w-2/3 flex items-center">
              <h1 className="text-[2.5vw] font-semibold">
                Kentucky Tusk
              </h1>
              <img src={ flag } className="h-[2vw] aspect-3/2 !ml-3 !mb-3" />
            </div>
            <div className="h-full w-1/3 flex items-center !mt-1 !mr-4">
              <HeaderButton buttonText="contact" icon={ messageIcon }/>
              <HeaderButton buttonText="follow" icon={ saveIcon }/>
            </div>
          </div>
          <div className="h-1/8 w-full flex !pt-2">
            <img src={ locationPin } className="h-full aspect-1/1" />
            <h2 className="font-semibold text-[1.3vw] !pl-3">
              Università di Bologna, University of Bologna [Italy]
            </h2>
          </div>
          <div className="h-5/8 w-full !pr-12 !pl-1 !pt-6 font-medium">
            <p>
              Lorem ipsum dolor sit amet consectetur, adipisicing elit. Soluta vero a neque. Quod tenetur in aut unde, dolorem saepe quasi cumque maiores impedit nobis provident deserunt consectetur beatae nesciunt nihil!
              Dignissimos error hic asperiores, reprehenderit ullam molestias sit cumque possimus iusto vel provident ea eum unde vitae corporis ratione illum voluptatibus consectetur. Error ratione voluptate voluptatem optio, omnis aliquid in!
            </p>
          </div>
        </div>
    </div>
  )
}

export default Header