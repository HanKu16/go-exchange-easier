import Header from "./Header"
import LikeButton from "./LikeButton"
import DislikeButton from "./DislikeButton"

const UniversityReview = () => {
  return (
    <div className="w-full bg-white !mb-5 rounded-2xl shadow-lg flex flex-col xl:!px-7 !px-3
      hover:brightness-97 hover:shadow-xl transition duration-150 ease-in">
      <Header/>
      <div className="xl:!px-1 xl:!pb-4 !pb-2">
        <p className="text-[1.2vh] lg:text-[2.2vh]">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Officia hic sequi fugit, tempora ipsam ducimus ex magnam aliquam mollitia aperiam accusamus quia illum quae, impedit quam veritatis, eaque voluptate a.
          Harum inventore necessitatibus unde libero consectetur laudantium ut ea placeat facere corrupti reprehenderit dolorum recusandae eaque explicabo nihil dolore repudiandae perferendis, quis sapiente maxime repellat quasi. Mollitia vel eaque corporis.
          Vel, ipsum soluta a reiciendis natus eius unde maiores, recusandae magnam repudiandae, expedita cupiditate. Odit dolorum voluptates necessitatibus, aliquam totam consequuntur sit, minima at tempore est esse iste cum praesentium?</p>
      </div>
      <div className="flex xl:h-[6vh] h-[4vh] !pb-3">
        <LikeButton/>
        <DislikeButton/>
      </div>
    </div>
  )
}

export default UniversityReview