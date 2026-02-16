import type { CountryDetails } from "../location/CountryDetails";
import type { UniversityDetails } from "../university/UniversityDetails";

export type UserDetails = {
  id: number;
  nick: string;
  country?: CountryDetails;
  university?: UniversityDetails;
};
