import type { SimplePage } from "../common/SimplePage";
import type { MessageDetails } from "../message/MessageDetails";

export type RoomDetails = {
  id: string;
  name: string;
  imageUrl?: string;
  lastMessages: SimplePage<MessageDetails>;
};
