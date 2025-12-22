import type { UniversitySummary } from "../summary/UniversitySummary";
import type { CountryDetails } from "./CountryDetails";

export type UserDetails = {
  id: number;
  nick: string;
  country?: CountryDetails;
  university?: UniversitySummary;
};
