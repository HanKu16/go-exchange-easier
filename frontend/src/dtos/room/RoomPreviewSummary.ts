import type { MessageSummary } from "../message/MessageSummary";

export type RoomPreviewSummary = {
  id: string;
  name: string;
  imageUrl: string;
  lastMessage: MessageSummary;
};
