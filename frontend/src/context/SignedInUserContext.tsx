import React, {
  useState,
  createContext,
  useContext,
  type ReactNode,
  useEffect,
} from "react";

type SignedInUser = {
  id: number;
  avatarUrl?: string;
  isSignedIn: boolean;
};

type SignedInUserContextType = {
  signedInUser: SignedInUser;
  isLoading: boolean;
  setSignedInUser: (signedInUser: SignedInUser) => void;
};

const SignedInUserContext = createContext<SignedInUserContextType | undefined>(
  undefined
);

type SignedInUserProviderProps = {
  children: ReactNode;
};

export const SignedInUserProvider: React.FC<SignedInUserProviderProps> = ({
  children,
}) => {
  const [signedInUser, setSignedInUser] = useState<SignedInUser>({
    id: 0,
    avatarUrl: undefined,
    isSignedIn: false,
  });
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function init() {
      setIsLoading(false);
    }
    init();
  }, []);

  return (
    <SignedInUserContext.Provider
      value={{ signedInUser, isLoading, setSignedInUser }}
    >
      {children}
    </SignedInUserContext.Provider>
  );
};

export const useSignedInUser = (): SignedInUserContextType => {
  const context = useContext(SignedInUserContext);
  if (!context) {
    throw new Error(
      "useSignedInUser must be used within a SignedInUserProvider"
    );
  }
  return context;
};
