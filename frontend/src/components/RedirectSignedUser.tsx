import { Navigate, useLocation } from "react-router-dom";
import { Outlet } from "react-router-dom";
import { useSignedInUser } from "../context/SignedInUserContext";

const RedirectSignedUser = () => {
  const { signedInUser } = useSignedInUser();
  const location = useLocation();

  if (signedInUser.isSignedIn) {
    const path = `/users/${signedInUser.id}`;
    return (
      <Navigate
        to={path}
        state={{ from: location.pathname + location.search }}
        replace
      />
    );
  }

  return <Outlet />;
};

export default RedirectSignedUser;
