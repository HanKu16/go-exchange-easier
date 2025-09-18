import type { UserRegistrationRequest } from "../dtos/user/UserRegistrationRequest"
import type { UserRegistrationResponse } from "../dtos/user/UserRegistrationResponse"
import { sendRequest } from "./send-request";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";

export const sendUserRegistrationRequest = async (body: UserRegistrationRequest): 
  Promise<ResponseSuccessResult<UserRegistrationResponse> | RepsonseFailureResult> => {
  const uri: string = "http://localhost:8080/api/auth/register"
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body)
  }
  return await sendRequest(uri, request)
}