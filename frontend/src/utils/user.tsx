import type { UserRegistrationRequest } from '../dtos/user/UserRegistrationRequest'
import type { UserRegistrationResponse } from '../dtos/user/UserRegistrationResponse'
import type { GetUserProfileResponse } from '../dtos/user/GetUserProfileResponse'
import { sendRequest } from './send-request'
import type { ResponseSuccessResult } from '../types/ResonseSuccessResult'
import type { RepsonseFailureResult } from '../types/ResponseFailureResult'
import type { GetUniversityReviewResponse } from '../dtos/review/GetUniversityReviewResponse'
import type { GetUserExchangeResponse } from '../dtos/user/GetUserExchangeResponse'
import type { UpdateUserDescriptionRequest } from '../dtos/user/UpdateUserDescriptionRequest'
import type { UpdateUserDescriptionResponse } from '../dtos/user/UpdateUserDescriptionResponse'
import type { AssignHomeUniversityRequest } from '../dtos/user/AssignHomeUniversityRequest'
import type { AssignHomeUniversityResponse } from '../dtos/user/AssignHomeUniversityResponse'
import type { UpdateUserStatusRequest } from '../dtos/user/UpdateUserStatusRequest'
import type { UpdateUserStatusResponse } from '../dtos/user/UpdateUserStatusResponse'
import type { AssignCountryOfOriginRequest } from '../dtos/user/AssignCountryOfOriginRequest'
import type { AssignCountryOfOriginResponse } from '../dtos/user/AssignCountryOfOriginResponse'

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
  const jwtToken = localStorage.getItem('jwtToken')
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
  const jwtToken = localStorage.getItem('jwtToken')
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
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest<GetUserExchangeResponse[]>(uri, request)
}

export const sendUpdateDescriptionRequest = async (body: UpdateUserDescriptionRequest):
  Promise<ResponseSuccessResult<UpdateUserDescriptionResponse> | 
    RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/description`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'PATCH',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
  return await sendRequest<UpdateUserDescriptionResponse>(uri, request)
}

export const sendAssignHomeUniversityRequest = async (body: AssignHomeUniversityRequest):
  Promise<ResponseSuccessResult<AssignHomeUniversityResponse> | 
    RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/homeUniversity`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'PATCH',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
  return await sendRequest<AssignHomeUniversityResponse>(uri, request)
}

export const sendUpdateStatusRequest = async (body: UpdateUserStatusRequest):
  Promise<ResponseSuccessResult<UpdateUserStatusResponse> | 
    RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/status`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'PATCH',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
  return await sendRequest<UpdateUserStatusResponse>(uri, request)
}

export const sendAssignCountryOfOriginRequest = async (body: AssignCountryOfOriginRequest):
  Promise<ResponseSuccessResult<AssignCountryOfOriginResponse> | 
    RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/users/countryOfOrigin`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'PATCH',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
  return await sendRequest<AssignCountryOfOriginResponse>(uri, request)
}

export const getSignedInUserId = (): string => {
  const userId: string | null = localStorage.getItem('userId')
  if (userId === null) {
    throw new Error('Did not found userId')    
  }
  return userId
}