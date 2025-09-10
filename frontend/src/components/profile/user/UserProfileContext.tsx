import React, { createContext, useState, useContext } from "react";
import type { ReactNode } from "react";

interface UserProfile {
  id: number;
  nick: string;
  description: string;
  isFollowed: boolean;
  homeUniversity?: {
    id: number;
    nativeName: string;
    englishName?: string;
  }
  countryOfOrigin?: {
    id: number;
    name: string;
  }
  status?: {
    id: number;
    name: string;
  }
}

interface UserProfileContextType {
  user: UserProfile | null;
  setUser: React.Dispatch<React.SetStateAction<UserProfile | null>>;
}

export const UserProfileContext = createContext<UserProfileContextType | undefined>(undefined);

export const UserProfileContextProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<UserProfile | null>(null);

  return (
    <UserProfileContext.Provider value={{ user, setUser }}>
      {children}
    </UserProfileContext.Provider>
  );
};

export const useUserProfileContext = (): UserProfileContextType => {
  const context = useContext(UserProfileContext);
  if (!context) {
    throw new Error("useUserProfileContext must be used within a UserProfileContextProvider");
  }
  return context;
};
