export type DislikeButtonProps =  {
  count: number;
  reaction: {
    typeId: number;
    name: "dislike";
  }
  universityReviewId: number;
  isSet: boolean;
}