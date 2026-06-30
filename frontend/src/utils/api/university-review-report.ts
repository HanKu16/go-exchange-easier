import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequest } from "../send-request";
import type { CreateUniversityReviewReportRequest } from "../../dtos/report/university/review/CreateUniversityReviewReport";
import type { UniversityReviewReportDetails } from "../../dtos/report/university/review/UniversityReviewReportDetails";

export const sendCreateUniversityReviewReportRequest = async (
  reviewId: number,
  body: CreateUniversityReviewReportRequest,
): Promise<
  ResponseSuccessResult<UniversityReviewReportDetails> | ResponseFailureResult
> => {
  const uri: string = `/api/reports/university-reviews/${reviewId}`;
  const request: RequestInit = {
    method: "POST",
    body: JSON.stringify(body),
  };
  return await sendRequest<UniversityReviewReportDetails>(uri, request);
};