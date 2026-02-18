import type { MessageSummary } from "../message/MessageSummary";

export type RoomSummary = {
  id: string;
  name: string;
  imageUrl: string;
  lastMessage: MessageSummary;
};
