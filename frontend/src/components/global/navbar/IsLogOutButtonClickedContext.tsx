import React, { createContext, useState, useContext } from "react";

interface IsLogOutButtonClickedContextType {
  isLogOutButtonClicked: boolean;
  setIsLogOutButtonClicked: React.Dispatch<React.SetStateAction<boolean>>;
}

const IsLogOutButtonClickedContext = createContext<
  IsLogOutButtonClickedContextType | undefined>(undefined);

export const IsLogOutButtonClickedContextProvider: React.FC<{
  children: React.ReactNode;
}> = ({ children }) => {
  const [isLogOutButtonClicked, setIsLogOutButtonClicked] = useState(false);

  return (
    <IsLogOutButtonClickedContext.Provider
      value={{ isLogOutButtonClicked, setIsLogOutButtonClicked }}
    >
      {children}
    </IsLogOutButtonClickedContext.Provider>
  );
};

export function useIsLogOutButtonClickedContext() {
  const context = useContext(IsLogOutButtonClickedContext);
  if (!context) {
    throw new Error(
      "useIsLogOutButtonClickedContext must be used within an " + 
      "IsLogOutButtonClickedContextProvider"
    );
  }
  return context;
}
