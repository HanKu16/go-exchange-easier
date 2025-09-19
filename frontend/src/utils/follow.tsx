import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";

export const sendFollowUserRequest = async (followeeId: number | string):
  Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/${followeeId}/follow`
  const jwtToken = localStorage.getItem("jwtToken")
  const request: RequestInit = {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
  }
  return await sendRequest(uri, request)
}

export const sendUnfollowUserRequest = async (followeeId: number | string):
  Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/${followeeId}/follow`
  const jwtToken = localStorage.getItem("jwtToken")
  const request: RequestInit = {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
  }
  return await sendRequest(uri, request)
}