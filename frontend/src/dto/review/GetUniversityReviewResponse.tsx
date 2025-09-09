export interface GetUniversityReviewResponse {
  id: number;
  author: {
    id: number;
    nick: string;
  };
  university: {
    id: number;
    englishName?: string;
    nativeName: string;
  };
  starRating: number;
  textContent: string;
  createdAt: string;
  reactions: {
    typeId: number;
    name: string;
    count: number;
    isSet: boolean;
  }[]
}
