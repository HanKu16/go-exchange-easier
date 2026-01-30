import type { TimeRangeSummary } from "../summary/TimeRangeSummary";
import type { UserSummary } from "../summary/UserSummary";
import type { UniversityDetails } from "./UniversityDetails";
import type { UniversityMajorDetails } from "./UniversityMajorDetails";

export type ExchangeDetails = {
  id: number;
  timeRange: TimeRangeSummary;
  user: UserSummary;
  university: UniversityDetails;
  major: UniversityMajorDetails;
};
