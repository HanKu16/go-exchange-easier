import type { UserStatusSummary } from "../dtos/summary/UserStatusSummary";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import type { Listing } from "../dtos/common/Listing";

export const sendGetUserStatusesRequest = async (): Promise<
  ResponseSuccessResult<Listing<UserStatusSummary>> | ResponseFailureResult
> => {
  const uri: string = `/api/userStatuses`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<Listing<UserStatusSummary>>(uri, request);
};
