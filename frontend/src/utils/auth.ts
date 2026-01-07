import type { LoginRequest } from "../dtos/auth/LoginRequest";
import type { LoginResponse } from "../dtos/auth/LoginResponse";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import type { UserRegistrationRequest } from "../dtos/auth/UserRegistrationRequest";
import type { UserRegistrationResponse } from "../dtos/auth/UserRegistrationResponse";

export const sendLoginRequest = async (
  body: LoginRequest
): Promise<ResponseSuccessResult<LoginResponse> | RepsonseFailureResult> => {
  const uri: string = `/api/auth/login`;
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest<LoginResponse>(uri, request);
};

export const sendUserRegistrationRequest = async (
  body: UserRegistrationRequest
): Promise<
  ResponseSuccessResult<UserRegistrationResponse> | RepsonseFailureResult
> => {
  const uri: string = `/api/auth/register`;
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};
