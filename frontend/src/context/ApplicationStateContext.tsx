import { useState, createContext, useContext, type ReactNode } from "react";

export type ApplicationState =
  | "loading"
  | "connectionError"
  | "serverError"
  | "success"
  | undefined;

export type ApplicationStateContextType = {
  appState: ApplicationState;
  setAppState: (state: ApplicationState) => void;
};

const ApplicationStateContext = createContext<
  ApplicationStateContextType | undefined
>(undefined);

export const ApplicationStateProvider = ({
  children,
}: {
  children: ReactNode;
}) => {
  const [appState, setAppState] = useState<ApplicationState | undefined>(
    undefined,
  );

  return (
    <ApplicationStateContext.Provider value={{ appState, setAppState }}>
      {children}
    </ApplicationStateContext.Provider>
  );
};

export const useApplicationState = (): ApplicationStateContextType => {
  const context = useContext(ApplicationStateContext);
  if (!context) {
    throw new Error(
      "useApplicationState must be used within a ApplicationStateProvider",
    );
  }
  return context;
};
