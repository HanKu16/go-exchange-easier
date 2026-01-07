import type { UserStatusSummary } from "../dtos/summary/UserStatusSummary";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import { getSignedInUserJwtToken } from "./user";
import type { Listing } from "../dtos/common/Listing";

export const sendGetUserStatusesRequest = async (): Promise<
  ResponseSuccessResult<Listing<UserStatusSummary>> | RepsonseFailureResult
> => {
  const uri: string = `/api/userStatuses`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<UserStatusSummary>>(uri, request);
};
