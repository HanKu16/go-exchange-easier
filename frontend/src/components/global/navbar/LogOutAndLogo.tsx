import { useIsLogOutButtonClickedContext } from "./IsLogOutButtonClickedContext.tsx"
import LogOut from "./LogOut"
import Logo from "./Logo";
import LogOutPopUp from "./LogOutPopUp.tsx";

const LogOutAndLogo = () => {
  const { isLogOutButtonClicked } = useIsLogOutButtonClickedContext()

  return (
    <>
      { isLogOutButtonClicked ? (
        <div className="w-3/9 h-full">
          <LogOutPopUp/>
          </div>
        ) : (
        <div className="flex w-3/9 h-full">
          <LogOut />
          <Logo/>
        </div>
        )
      }
    </>
  )
}

export default LogOutAndLogo