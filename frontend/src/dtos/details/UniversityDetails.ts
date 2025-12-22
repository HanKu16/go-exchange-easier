import type { CityDetails } from "./CityDetails";

export type UniversityDetails = {
  id: number;
  nativeName: string;
  englishName?: string;
  city: CityDetails;
};
