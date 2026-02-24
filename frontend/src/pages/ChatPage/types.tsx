export type RoomBoxProps = {
  id: string;
  name: string;
  avatarUrl?: string;
  lastMessage?: {
    createdAt: string;
    textContent: string;
  };
};

export type RoomHeaderProps = {
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
};
