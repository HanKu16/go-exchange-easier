export interface GetUserProfileResponse {
  userId: number;
  nick: string;
  description: string;
  isFollowed: boolean;
  homeUniversity?: {
    id: number;
    nativeName: string;
    englishName?: string;
  };
  countryOfOrigin?: {
  id: number;
  name: string;
  };
  status?: {
    id: number;
    name: string;
  };
}