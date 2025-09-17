export type LikeButtonProps =  {
  count: number;
  reaction: {
    typeId: number;
    name: "like";
  }
  universityReviewId: number;
  isSet: boolean;
}