import type { CountrySummary } from "../location/CountrySummary";
import type { UniversitySummary } from "../university/UniversitySummary";
import type { UserStatusSummary } from "./status/UserStatusSummary";

export type UserProfileDetails = {
  userId: number;
  nick: string;
  avatarUrl?: string;
  description: string;
  isFollowed: boolean;
  homeUniversity?: UniversitySummary;
  countryOfOrigin?: CountrySummary;
  status?: UserStatusSummary;
};
