export interface GetUniversityReviewResponse {
  id: Number;
  author: {
    id: Number;
    nick: String;
  };
  university: {
    id: Number;
    englishName: String;
    nativeName: String;
  };
  starRating: Number;
  textContent: String;
  createdAt: String;
  reactions: {
    typeId: Number;
    name: String;
    count: Number;
    isSet: Boolean;
  }[]
}
