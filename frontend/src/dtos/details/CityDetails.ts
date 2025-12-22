import type { CountryDetails } from "./CountryDetails";

export type CityDetails = {
  id: number;
  name: string;
  country: CountryDetails;
};
