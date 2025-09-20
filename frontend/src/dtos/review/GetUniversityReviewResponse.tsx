import type { ReactionName } from "../../types/ReactionName";

export type GetUniversityReviewResponse = {
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
    name: ReactionName;
    count: number;
    isSet: boolean;
  }[]
}
