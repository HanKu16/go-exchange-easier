export type RoomBoxProps = {
  id: string;
  name: string;
  hasAnyUnreadMessages: boolean;
  avatarUrl?: string;
  lastMessage?: {
    createdAt: string;
    textContent: string;
  };
};

export type HeaderProps = {
  id: string;
  name: string;
  avatarUrl?: string;
  link: string;
};

export type MessageBoxProps = {
  id: string;
  textContent: string;
  nick: string;
  avatarUrl?: string;
  dateAndTime: string;
  isUserMessage: boolean;
  isPending: boolean;
  onDelete: () => Promise<boolean>;
};

export const cacheKeys = {
  newRoom: ["new-room"] as const,
  allRooms: ["rooms"] as const,
  room: (roomId: string) => ["room", roomId] as const,
  messagesFromRoom: (roomId: string) => ["messages", roomId] as const,
};

export type MessageStatus = "success" | "pending" | "error";

export const temporaryMessagePrefix = "temp-";

export type MessageInputProps = {
  disabled?: boolean;
};

export type LoadingErrorProps = {
  onRetry: () => void;
};
