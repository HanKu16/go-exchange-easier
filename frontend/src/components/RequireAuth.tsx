import { Navigate } from "react-router-dom";
import { Outlet } from "react-router-dom";
import LoadingPage from "../pages/LoadingPage";
import { useSignedInUser } from "../context/SignedInUserContext";

const RequireAuth = () => {
  const { signedInUser, isLoading } = useSignedInUser();

  if (isLoading) {
    return (
      <LoadingPage
        backgroundColor="#eeececff"
        circularProgressColor="#182c44"
        text=""
      />
    );
  }

  const isAuthenticated = signedInUser.isSignedIn;
  console.log(signedInUser);

  if (!isAuthenticated) {
    return (
      <Navigate
        to="/login"
        state={{ from: location.pathname + location.search }}
        replace
      />
    );
  }

  return <Outlet />;
};

export default RequireAuth;
