import type { UniversitySummary } from "../UniversitySummary";
import type { UserWithAvatarSummary } from "../../user/UserWithAvatarSummary";
import type { ReactionDetails } from "../../reaction/ReactionDetails";

export type UniversityReviewDetails = {
  id: number;
  author: UserWithAvatarSummary;
  university: UniversitySummary;
  starRating: number;
  textContent: string;
  createdAt: string;
  reactions: ReactionDetails[];
};
