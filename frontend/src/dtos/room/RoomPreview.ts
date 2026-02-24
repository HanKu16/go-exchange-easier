import type { MessageSummary } from "../message/MessageSummary";

export type RoomPreview = {
  id: string;
  name: string;
  imageUrl?: string;
  lastMessage: MessageSummary;
};
