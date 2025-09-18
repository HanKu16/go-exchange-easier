import type { LoginRequest } from "../dtos/auth/LoginRequest"
import type { LoginResponse } from "../dtos/auth/LoginResponse";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";

export const sendLoginRequest = async (body: LoginRequest) :
  Promise<ResponseSuccessResult<LoginResponse> | RepsonseFailureResult> => {
  const uri: string = 'http://localhost:8080/api/auth/login'
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body)
  }
  return await sendRequest<LoginResponse>(uri, request)
}
