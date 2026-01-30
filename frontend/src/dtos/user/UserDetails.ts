import type { UniversitySummary } from "../university/UniversitySummary";
import type { CountryDetails } from "../location/CountryDetails";

export type UserDetails = {
  id: number;
  nick: string;
  country?: CountryDetails;
  university?: UniversitySummary;
};
