import type { ResponseSuccessResult } from '../types/ResonseSuccessResult'
import type { RepsonseFailureResult } from '../types/ResponseFailureResult'
import { sendRequest } from './send-request'
import { API_BASE_URL } from '../config/api'
import { getSignedInUserJwtToken } from './user'

export const sendFollowUserRequest = async (followeeId: number | string):
  Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/users/${followeeId}/follow`
  const jwtToken = getSignedInUserJwtToken()
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
  const uri: string = `${API_BASE_URL}/api/users/${followeeId}/follow`
  const jwtToken = getSignedInUserJwtToken()
  const request: RequestInit = {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
  }
  return await sendRequest(uri, request)
}

export const sendFollowUniversityRequest = async (universityId: number | string) :
  Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/universities/${universityId}/follow`
  const jwtToken = getSignedInUserJwtToken()
  const request: RequestInit = {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest(uri, request)
}

export const sendUnfollowUniversityRequest = async (universityId: number | string) :
  Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/universities/${universityId}/follow`
  const jwtToken = getSignedInUserJwtToken()
  const request: RequestInit = {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest(uri, request)
}