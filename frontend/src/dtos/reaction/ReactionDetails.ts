import type { ReactionName } from "../../types/ReactionName";

export type ReactionDetails = {
  typeId: number;
  name: ReactionName;
  count: number;
  isSet: boolean;
};
