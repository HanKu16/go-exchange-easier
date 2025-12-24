import type { GetUserStatusResponse } from "../dtos/user-status/GetUserStatusResponse";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import { API_BASE_URL } from "../config/api";
import { getSignedInUserJwtToken } from "./user";

export const sendGetUserStatusesRequest = async (): Promise<
  ResponseSuccessResult<GetUserStatusResponse[]> | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/userStatuses`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<GetUserStatusResponse[]>(uri, request);
};
