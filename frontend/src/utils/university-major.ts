import type { UniversityMajorSummary } from "../dtos/summary/UniversityMajorSummary";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import type { Listing } from "../dtos/common/Listing";

export const sendGetUniversityMajorsRequest = async (): Promise<
  ResponseSuccessResult<Listing<UniversityMajorSummary>> | ResponseFailureResult
> => {
  const uri: string = `/api/universityMajors`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<Listing<UniversityMajorSummary>>(uri, request);
};
