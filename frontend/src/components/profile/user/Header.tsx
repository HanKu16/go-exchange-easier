import Avatar from "./Avatar"
import Nick from "./Nick"
import HomeUniversity from "./HomeUniversity"
import FollowButton from "./FollowButton"
import ContactButton from "./ContactButton"
  
const Header = () => {
  return (
    <div className="bg-sunny-yellow flex flex-col overflow-hidden text-dark-blue
      rounded-tl-4xl rounded-tr-4xl  
      w-[92vw] h-[25vh]
      md:h-[30vh]
      xl:h-[45vh] xl:w-[80vw]">
      <div className="w-full xl:h-full flex h-1/2">
        <Avatar />
        <div className="w-full flex flex-col xl:!pl-8 !pl-6 xl:h-full h-full">
          <div className="h-1/3 xl:h-1/4 w-full flex">
            <Nick/>
            <div className={"hidden xl:h-full xl:w-1/3 flex xl:!pt-6 w-full xl:!mr-2 xl:flex xl:flex-row xl:justify-end"}>
              <ContactButton/>
              <FollowButton/>
            </div>
          </div>
          <HomeUniversity/>
          <div className="xl:hidden flex h-2/5 w-2/5 justify-around">
            <ContactButton/>
            <FollowButton/>
          </div>
          <div className="hidden xl:flex h-5/8 w-full !pr-12 !pl-1 !pt-6 font-medium">
            <p>
              Lorem ipsum dolor sit amet consectetur, adipisicing elit. Soluta vero a neque. Quod tenetur in aut unde, dolorem saepe quasi cumque maiores impedit nobis provident deserunt consectetur beatae nesciunt nihil!
              Dignissimos error hic asperiores, reprehenderit ullam molestias sit cumque possimus iusto vel provident ea eum unde vitae corporis ratione illum voluptatibus consectetur. Error ratione voluptate voluptatem optio, omnis aliquid in!
            </p>
          </div>
        </div>
      </div>
      <div className="xl:hidden text-[2vw] !px-3 !pt-5">
        <p>
          Lorem ipsum dolor sit amet consectetur, adipisicing elit. Soluta vero a neque. Quod tenetur in aut unde, dolorem saepe quasi cumque maiores impedit nobis provident deserunt consectetur beatae nesciunt nihil!
          Dignissimos error hic asperiores, reprehenderit ullam molestias sit cumque possimus iusto vel provident ea eum unde vitae corporis ratione illum voluptatibus consectetur. Error ratione voluptate voluptatem optio, omnis aliquid in!
        </p>
      </div>
    </div>
  )
}

export default Header


