import profileExampleImage from "../../../assets/profile/user/profile-example-image.png"

const Avatar = () => {
  return (
    <div className="bg-dirty-white brightness-90 
      h-full aspect-square 
      xl:h-full xl:aspect-square ">
      <img src={ profileExampleImage } className="w-full h-full" />
    </div>
  )
}

export default Avatar