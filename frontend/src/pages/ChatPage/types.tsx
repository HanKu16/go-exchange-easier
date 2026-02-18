export type ConversationBoxProps = {
  id: string;
  name: string;
  avatarUrl?: string;
  lastMessage?: {
    createdAt: string;
    textContent: string;
  };
};
