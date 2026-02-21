import type { AuthorDetails } from "./AuthorDetails";

export type MessageDetails = {
  id: string;
  createdAt: string;
  textContent: string;
  author: AuthorDetails;
};
