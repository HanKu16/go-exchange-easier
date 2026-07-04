import type { MessageSummary } from "../message/MessageSummary";

export type RoomPreview = {
  id: string;
  name: string;
  targetUserId: string;
  hasAnyUnreadMessages: boolean;
  imageUrl?: string;
  lastMessage: MessageSummary;
};
