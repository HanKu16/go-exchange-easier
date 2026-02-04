import type { ReactionType } from "../../types/ReactionType";

export type ReactionDetails = {
  type: ReactionType;
  count: number;
  isSet: boolean;
};
