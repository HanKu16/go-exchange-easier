import type { AuthorSummary } from "./AuthorSummary";

export type MessageSummary = {
  createdAt: string;
  textContent: string;
  author: AuthorSummary;
};
