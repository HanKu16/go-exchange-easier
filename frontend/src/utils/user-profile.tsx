import type { GetUniversityReviewResponse } from "../dto/review/GetUniversityReviewResponse";
import type { GetUserProfileResponse } from "../dto/user/GetUserProfileResponse"
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";

export const sendGetUserProfileRequest = async (userId: number) :
  Promise<ResponseSuccessResult<GetUserProfileResponse> | RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/${userId}/profile`
  const jwtToken = localStorage.getItem("jwtToken")
  const request: RequestInit = {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    }
  }
  return await sendRequest<GetUserProfileResponse>(uri, request)
}

export const sendGetUserReviewsRequest = async (userId: number) => {
  const uri: string = `http://localhost:8080/api/users/${userId}/universityReviews`
  const jwtToken = localStorage.getItem("jwtToken")
  const request: RequestInit = {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    }
  }
  return await sendRequest<GetUniversityReviewResponse[]>(uri, request)
}