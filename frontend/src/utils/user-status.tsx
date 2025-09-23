import type { GetUserStatusResponse } from "../dtos/user-status/GetUserStatusResponse"
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult"
import type { RepsonseFailureResult } from "../types/ResponseFailureResult"
import { sendRequest } from "./send-request"

export const sendGetUserStatusesRequest = async () :
  Promise<ResponseSuccessResult<GetUserStatusResponse[]> | RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/userStatuses`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest<GetUserStatusResponse[]>(uri, request)
}