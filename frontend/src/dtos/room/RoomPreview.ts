import type { MessageSummary } from "../message/MessageSummary";

export type RoomPreview = {
  id: string;
  name: string;
  targetUserId: number;
  imageUrl?: string;
  lastMessage: MessageSummary;
};
