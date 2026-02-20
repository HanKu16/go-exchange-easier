import type { AuthorSummary } from "./AuthorSummary";

export type MessageDetails = {
  id: string;
  createdAt: string;
  textContent: string;
  author: AuthorSummary;
};
