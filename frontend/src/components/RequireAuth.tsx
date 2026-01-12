import { Navigate } from "react-router-dom";
import { Outlet } from "react-router-dom";
import LoadingPage from "../pages/LoadingPage";
import { useSignedInUser } from "../context/SignedInUserContext";
import { sendGetMe } from "../utils/user";
import { useEffect, useState } from "react";
import { useSnackbar } from "../context/SnackBarContext";

const RequireAuth = () => {
  const { signedInUser, isLoading, setSignedInUser } = useSignedInUser();
  const [isVerifying, setIsVerifying] = useState(true);
  const { showAlert } = useSnackbar();

  useEffect(() => {
    const verifyUser = async () => {
      if (signedInUser.isSignedIn) {
        setIsVerifying(false);
        return;
      }
      try {
        const result = await sendGetMe();
        if (result.isSuccess) {
          setSignedInUser({ id: result.data.id, isSignedIn: true });
        } else {
          setSignedInUser({ id: 0, isSignedIn: false });
        }
      } catch (error) {
        showAlert("An error occured.", "error");
      } finally {
        setIsVerifying(false);
      }
    };
    verifyUser();
  }, []);

  if (isLoading || isVerifying) {
    return (
      <LoadingPage
        backgroundColor="#eeececff"
        circularProgressColor="#182c44"
        text="Loading..."
      />
    );
  }

  if (!signedInUser.isSignedIn) {
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
