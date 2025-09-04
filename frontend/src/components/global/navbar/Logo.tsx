import navbarLogoIconImage  from "../../../assets/global/navbar/navbar-logo.png"

const Logo = () => {
  return (
    <div className="h-full w-4/5 flex justify-center items-center">
      <img src={ navbarLogoIconImage } alt="Go Exchange Easier logo" 
        className="f-full w-9/10"/>
    </div>
  )
}

export default Logo