import type { LoginRequest } from "../dtos/auth/LoginRequest";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../types/ResponseFailureResult";
import { sendRequestWithoutRefresh } from "./send-request";
import type { UserRegistrationRequest } from "../dtos/auth/UserRegistrationRequest";
import type { UserRegistrationResponse } from "../dtos/auth/UserRegistrationResponse";
import { getDeviceId, getReadableDeviceName } from "./device";
import type { SignedInUserSummary } from "../dtos/summary/SignedInUserSummary";

export const sendLoginRequest = async (
  body: LoginRequest
): Promise<
  ResponseSuccessResult<SignedInUserSummary> | ResponseFailureResult
> => {
  const uri: string = `/api/auth/login`;
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "X-Device-Id": getDeviceId(),
      "X-Device-Name": getReadableDeviceName(),
    },
    body: JSON.stringify(body),
  };
  return await sendRequestWithoutRefresh(uri, request);
};

export const sendUserRegistrationRequest = async (
  body: UserRegistrationRequest
): Promise<
  ResponseSuccessResult<UserRegistrationResponse> | ResponseFailureResult
> => {
  const uri: string = `/api/auth/register`;
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequestWithoutRefresh(uri, request);
};

export const sendLogoutRequest = async (): Promise<
  ResponseSuccessResult<void> | ResponseFailureResult
> => {
  const uri: string = `/api/auth/logout`;
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "X-Device-Id": getDeviceId(),
    },
  };
  return await sendRequestWithoutRefresh(uri, request);
};

let activeRefreshPromise: Promise<
  ResponseSuccessResult<void> | ResponseFailureResult
> | null = null;

export const sendRefreshRequest = async (): Promise<
  ResponseSuccessResult<void> | ResponseFailureResult
> => {
  if (activeRefreshPromise) {
    return activeRefreshPromise;
  }

  activeRefreshPromise = (async () => {
    try {
      const uri: string = "/api/auth/refresh";
      const request: RequestInit = {
        method: "POST",
      };
      return await sendRequestWithoutRefresh(uri, request);
    } finally {
      activeRefreshPromise = null;
    }
  })();
  return activeRefreshPromise;
};
