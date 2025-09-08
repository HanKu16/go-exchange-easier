export interface GetUserProfileResponse {
  userId: Number;
  nick: String;
  description: String;
  isFollowed: Boolean;
  homeUniversity: {
    id: Number;
    nativeName: String;
    englishName: String;
  };
  countryOfOrigin: {
  id: Number;
  name: String;
  };
  status: {
    id: Number;
    name: String;
  };
}