import type { UserStatusSummary } from "../dtos/summary/UserStatusSummary";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import type { Listing } from "../dtos/common/Listing";

export const sendGetUserStatusesRequest = async (): Promise<
  ResponseSuccessResult<Listing<UserStatusSummary>> | RepsonseFailureResult
> => {
  const uri: string = `/api/userStatuses`;
  const request: RequestInit = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<UserStatusSummary>>(uri, request);
};
