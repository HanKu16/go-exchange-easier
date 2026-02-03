import type { LoginRequest } from "../../dtos/auth/LoginRequest";
import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequestWithoutRefresh } from "../send-request";
import type { UserRegistrationRequest } from "../../dtos/auth/UserRegistrationRequest";
import type { RegistrationSummary } from "../../dtos/auth/RegistratationSummary";
import { getDeviceId } from "../device";

export const sendLoginRequest = async (
  body: LoginRequest,
): Promise<ResponseSuccessResult<void> | ResponseFailureResult> => {
  const uri: string = `/api/auth/login`;
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequestWithoutRefresh(uri, request);
};

export const sendRegistrationRequest = async (
  body: UserRegistrationRequest,
): Promise<
  ResponseSuccessResult<RegistrationSummary> | ResponseFailureResult
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
