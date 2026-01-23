import type { UniversitySummary } from "../summary/UniversitySummary";
import type { UserWithAvatarSummary } from "../summary/UserWithAvatarSummary";
import type { ReactionDetails } from "./ReactionDetails";

export type UniversityReviewDetails = {
  id: number;
  author: UserWithAvatarSummary;
  university: UniversitySummary;
  starRating: number;
  textContent: string;
  createdAt: string;
  reactions: ReactionDetails[];
};
