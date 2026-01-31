import type { CityDetails } from "../location/CityDetails";

export type UniversityDetails = {
  id: number;
  nativeName: string;
  englishName?: string;
  city: CityDetails;
};
