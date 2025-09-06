import UniversityReview from "../../global/review/UniversityReview"

const Feed = () => {
  return (
    <div className="flex flex-col bg-dirty-white xl:w-[80vw] h-1/2 brightness-95 xl:!px-15 !px-5 !pt-3 w-[92vw]">
      <h2 className="text-neutral-500 xl:text-[1.4vw] text-[2.5vw] xl:!py-5 !pb-3 font-bold">
        University Reviews written by Ducky Kentucky
      </h2>
      <div className="flex flex-col">
        <UniversityReview/>
        <UniversityReview/>
        <UniversityReview/>
        <UniversityReview/>
        <UniversityReview/>
      </div>
    </div>
  )
}

export default Feed