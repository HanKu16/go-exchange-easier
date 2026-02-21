import { CssBaseline } from "@mui/material";
import LoginPage from "./pages/LoginPage";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
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
import { ApplicationStateProvider } from "./context/ApplicationStateContext";
import AppShell from "./components/AppShell";
import NotFoundPage from "./pages/NotFoundPage";
import SearchOffIcon from "@mui/icons-material/SearchOff";
import TermsAndConditionsPage from "./pages/TermsAndConditionsPage";
import { ConfirmationDialogProvider } from "./context/ConfirmationDialogContext";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import ChatPage from "./pages/ChatPage";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      staleTime: 1000 * 60 * 5,
    },
  },
});

const AppContent = () => {
  const { isLoading } = useSignedInUser();
  const { signedInUser } = useSignedInUser();

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
        <Route
          path="/terms-and-conditions"
          element={<TermsAndConditionsPage />}
        />
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
        <Route path="/chat" element={<ChatPage />} />
        <Route path="/chat/:roomId" element={<ChatPage />} />
        <Route
          path="*"
          element={
            !signedInUser.isSignedIn ? (
              <Navigate to="/login" replace />
            ) : (
              <NotFoundPage
                icon={SearchOffIcon}
                title={"Page not found"}
                subheader={
                  "The feature you want is not supported by Go Exchange Easier"
                }
              />
            )
          }
        />
      </Route>
    </Routes>
  );
};

export const App = () => {
  return (
    <BrowserRouter>
      <QueryClientProvider client={queryClient}>
        <CssBaseline />
        <SignedInUserProvider>
          <SnackbarProvider>
            <ConfirmationDialogProvider>
              <ApplicationStateProvider>
                <Routes>
                  <Route element={<AppShell />}>
                    <Route path="/*" element={<AppContent />}></Route>
                  </Route>
                </Routes>
              </ApplicationStateProvider>
            </ConfirmationDialogProvider>
          </SnackbarProvider>
        </SignedInUserProvider>
      </QueryClientProvider>
    </BrowserRouter>
  );
};

export default App;
