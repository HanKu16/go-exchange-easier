import type { UniversitySummary } from "../summary/UniversitySummary";
import type { UserSummary } from "../summary/UserSummary";
import type { ReactionDetails } from "./ReactionDetails";

export type UniversityReviewDetails = {
  id: number;
  author: UserSummary;
  university: UniversitySummary;
  starRating: number;
  textContent: string;
  createdAt: string;
  reactions: ReactionDetails[];
};
