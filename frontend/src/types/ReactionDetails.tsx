import type { ReactionName } from "./ReactionName";

export type ReactionDetails = {
    typeId: number;
    name: ReactionName;
    count: number;
    isSet: boolean;
}