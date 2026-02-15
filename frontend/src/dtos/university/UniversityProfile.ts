import type { CityDetails } from "../location/CityDetails";

export type UniversityProfile = {
  id: number;
  nativeName: string;
  englishName: string | null;
  linkToWebsite: string | null;
  city: CityDetails;
  isFollowed: boolean;
};
