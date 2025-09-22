import type { UserRegistrationRequest } from '../dtos/user/UserRegistrationRequest'
import type { UserRegistrationResponse } from '../dtos/user/UserRegistrationResponse'
import type { GetUserProfileResponse } from '../dtos/user/GetUserProfileResponse'
import { sendRequest } from './send-request'
import type { ResponseSuccessResult } from '../types/ResonseSuccessResult'
import type { RepsonseFailureResult } from '../types/ResponseFailureResult'
import type { GetUniversityReviewResponse } from '../dtos/review/GetUniversityReviewResponse'
import type { GetUserExchangeResponse } from '../dtos/user/GetUserExchangeResponse'

export const sendUserRegistrationRequest = async (body: UserRegistrationRequest): 
  Promise<ResponseSuccessResult<UserRegistrationResponse> | RepsonseFailureResult> => {
  const uri: string = 'http://localhost:8080/api/auth/register'
  const request: RequestInit = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
  return await sendRequest(uri, request)
}

export const sendGetUserProfileRequest = async (userId: number | string) :
  Promise<ResponseSuccessResult<GetUserProfileResponse> | RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/${userId}/profile`
  const jwtToken = localStorage.getItem("jwtToken")
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest<GetUserProfileResponse>(uri, request)
}

export const sendGetUserReviewsRequest = async (userId: number | string):
  Promise<ResponseSuccessResult<GetUniversityReviewResponse[]> | 
    RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/${userId}/universityReviews`
  const jwtToken = localStorage.getItem("jwtToken")
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest<GetUniversityReviewResponse[]>(uri, request)
}

export const sendGetUserExchangesRequest = async (userId: number | string):
  Promise<ResponseSuccessResult<GetUserExchangeResponse[]> | 
    RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/${userId}/exchanges`
  const jwtToken = localStorage.getItem("jwtToken")
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest<GetUserExchangeResponse[]>(uri, request)
}