import {
  useState,
  createContext,
  useContext,
  type ReactNode,
  useEffect,
} from "react";
import { sendGetMeRequest } from "../utils/user";

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
  undefined,
);

export const SignedInUserProvider = ({ children }: { children: ReactNode }) => {
  const [signedInUser, setSignedInUser] = useState<SignedInUser>({
    id: 0,
    avatarUrl: undefined,
    isSignedIn: false,
  });
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    let isMounted = true;
    const verifyUser = async () => {
      try {
        const result = await sendGetMeRequest();
        if (isMounted) {
          if (result.isSuccess) {
            setSignedInUser({ id: result.data.id, isSignedIn: true });
          } else {
            setSignedInUser({ id: 0, isSignedIn: false });
          }
        }
      } catch (error) {
        if (isMounted) {
          setSignedInUser({ id: 0, isSignedIn: false });
        }
      } finally {
        if (isMounted) {
          setIsLoading(false);
        }
      }
    };
    verifyUser();
    return () => {
      isMounted = false;
    };
  }, []);

  useEffect(() => {
    const handleForceLogout = () => {
      setSignedInUser({ id: 0, avatarUrl: undefined, isSignedIn: false });
    };

    window.addEventListener("auth:logout", handleForceLogout);

    return () => {
      window.removeEventListener("auth:logout", handleForceLogout);
    };
  }, []);

  return (
    <SignedInUserContext.Provider
      value={{ signedInUser, setSignedInUser, isLoading }}
    >
      {children}
    </SignedInUserContext.Provider>
  );
};

export const useSignedInUser = (): SignedInUserContextType => {
  const context = useContext(SignedInUserContext);
  if (!context) {
    throw new Error(
      "useSignedInUser must be used within a SignedInUserProvider",
    );
  }
  return context;
};
