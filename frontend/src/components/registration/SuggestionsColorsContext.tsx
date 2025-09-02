import { createContext, useContext } from "react"

export interface SuggestionsColors {
  login: string;
  password: string;
  mail: string;
  nick: string;
  [key: string]: string;
}

export const SuggestionsColorsContext = 
  createContext<SuggestionsColors | undefined>(undefined)

export const useColorsContext = (): SuggestionsColors => {
  const suggestiosColors = useContext(SuggestionsColorsContext)
  if (suggestiosColors === undefined) {
    throw new Error("useUserContext must be used with a SuggestionsColorsContext")
  }
  return suggestiosColors
}
