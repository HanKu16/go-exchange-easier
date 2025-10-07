export type GetUniversityProfileResponse = {
  id: number;
  nativeName: string;
  englishName: string | null;
  linkToWebsite: string | null;
  cityName: string;
  countryName: string;
  isFollowed: boolean;
}
