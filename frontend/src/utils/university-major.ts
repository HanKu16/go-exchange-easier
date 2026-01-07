import type { UniversityMajorSummary } from "../dtos/summary/UniversityMajorSummary";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import { getSignedInUserJwtToken } from "./user";
import type { Listing } from "../dtos/common/Listing";

export const sendGetUniversityMajorsRequest = async (): Promise<
  ResponseSuccessResult<Listing<UniversityMajorSummary>> | RepsonseFailureResult
> => {
  const uri: string = `/api/universityMajors`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<UniversityMajorSummary>>(uri, request);
};
