import type { FieldOfStudySummary } from "../field-of-study/FieldOfStudySummary";
import type { TimeRangeSummary } from "./TimeRangeSummary";
import type { UserSummary } from "../user/UserSummary";
import type { UniversityDetails } from "../university/UniversityDetails";

export type ExchangeDetails = {
  id: number;
  timeRange: TimeRangeSummary;
  user: UserSummary;
  university: UniversityDetails;
  major: FieldOfStudySummary;
};
