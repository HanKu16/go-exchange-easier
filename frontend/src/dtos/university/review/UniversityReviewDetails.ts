import type { UserWithAvatarSummary } from "../../user/UserWithAvatarSummary";
import type { ReactionDetails } from "../../reaction/ReactionDetails";
import type { UniversityDetails } from "../UniversityDetails";

export type UniversityReviewDetails = {
  id: number;
  author: UserWithAvatarSummary;
  university: UniversityDetails;
  starRating: number;
  textContent: string;
  createdAt: string;
  reactions: ReactionDetails[];
};
