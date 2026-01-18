import { Outlet } from "react-router-dom";
import { useApplicationState } from "../context/ApplicationStateContext";
import LoadingPage from "../pages/LoadingPage";
import ServiceUnavailablePage from "../pages/ServiceUnavailablePage";
import ServerErrorPage from "../pages/ServerErrorPage";

const AppShell = () => {
  const { appState } = useApplicationState();

  if (appState === "connectionError") return <ServiceUnavailablePage />;
  if (appState === "serverError") return <ServerErrorPage />;

  return (
    <>
      {appState === "loading" && (
        <div style={{ position: "fixed", inset: 0, zIndex: 9999 }}>
          <LoadingPage
            backgroundColor="#eeececff"
            circularProgressColor="#182c44"
            text="Loading"
          />
        </div>
      )}
      <div style={{ display: appState === "loading" ? "none" : "block" }}>
        <Outlet />
      </div>
    </>
  );
};

export default AppShell;
