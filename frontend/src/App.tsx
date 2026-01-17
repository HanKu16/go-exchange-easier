import { CssBaseline } from "@mui/material";
import LoginPage from "./pages/LoginPage";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import RegistrationPage from "./pages/RegistrationPage";
import UserProfilePage from "./pages/UserProfilePage";
import EditUserPage from "./pages/EditUserPage";
import UniversityProfilePage from "./pages/UniversityProfilePage";
import { SnackbarProvider } from "./context/SnackBarContext";
import RequireAuth from "./components/RequireAuth";
import SearchPage from "./pages/SearchPage";
import FollowPage from "./pages/FollowPage";
import {
  SignedInUserProvider,
  useSignedInUser,
} from "./context/SignedInUserContext";
import LoadingPage from "./pages/LoadingPage";
import RedirectSignedUser from "./components/RedirectSignedUser";

const AppContent = () => {
  const { isLoading } = useSignedInUser();

  if (isLoading) {
    return (
      <LoadingPage
        backgroundColor="#eeececff"
        circularProgressColor="#182c44"
        text="Loading..."
      />
    );
  }

  return (
    <Routes>
      <Route element={<RedirectSignedUser />}>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegistrationPage />} />
      </Route>
      <Route element={<RequireAuth />}>
        <Route path="/users/:userId" element={<UserProfilePage />} />
        <Route
          path="/universities/:universityId"
          element={<UniversityProfilePage />}
        />
        <Route path="/me" element={<EditUserPage />} />
        <Route path="/search" element={<SearchPage />} />
        <Route path="/follows" element={<FollowPage />} />
      </Route>
    </Routes>
  );
};

export const App = () => {
  return (
    <BrowserRouter>
      <CssBaseline />
      <SignedInUserProvider>
        <SnackbarProvider>
          <AppContent />
        </SnackbarProvider>
      </SignedInUserProvider>
    </BrowserRouter>
  );
};

export default App;
