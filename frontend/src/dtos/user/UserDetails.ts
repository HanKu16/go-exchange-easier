import type { UniversitySummary } from "../university/UniversitySummary";
import type { CountrySummary } from "../location/CountrySummary";

export type UserDetails = {
  id: number;
  nick: string;
  country?: CountrySummary;
  university?: UniversitySummary;
};
