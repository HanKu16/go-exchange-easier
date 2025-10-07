import { API_BASE_URL } from "../config/api"
import type { GetUniversityReviewResponse } from "../dtos/review/GetUniversityReviewResponse"
import type { GetReviewsCountResponse } from "../dtos/university/GetReviewsCountResponse"
import type { GetUniversityProfileResponse } from "../dtos/university/GetUniversityProfileResponse"
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult"
import type { RepsonseFailureResult } from "../types/ResponseFailureResult"
import { sendRequest } from "./send-request"

export const sendGetUniversityProfileRequest = async (universityId: number | string) :
  Promise<ResponseSuccessResult<GetUniversityProfileResponse> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/universities/${universityId}/profile`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest<GetUniversityProfileResponse>(uri, request)
}

export const sendGetUniversityReviewsRequest = async (
  universityId: number | string, page: number, size: number) :
  Promise<ResponseSuccessResult<GetUniversityReviewResponse[]> | RepsonseFailureResult> => {
  const params = {page: `${page}`, size: `${size}`};
  const searchParams = new URLSearchParams(params).toString();
  const uri: string = `${API_BASE_URL}/api/universities/${universityId}/reviews?${searchParams}`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
  }
  return await sendRequest<GetUniversityReviewResponse[]>(uri, request)
}

export const sendGetReviewsCountRequest = async (universityId: number | string) :
  Promise<ResponseSuccessResult<GetReviewsCountResponse> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/universities/${universityId}/reviews/count`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest<GetReviewsCountResponse>(uri, request)
}