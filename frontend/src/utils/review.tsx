import type { AddUniversityReviewReactionRequest } from '../dtos/review/AddUniversityReviewReactionRequest'
import type { ResponseSuccessResult } from '../types/ResonseSuccessResult'
import type { RepsonseFailureResult } from '../types/ResponseFailureResult'
import { sendRequest } from './send-request'
import { API_BASE_URL } from '../config/api'

export const sendAddUniversityReviewReactionRequest = async (reviewId: number, 
  body: AddUniversityReviewReactionRequest):
  Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/universityReview/${reviewId}/reaction`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'PUT',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
  return await sendRequest(uri, request)
}

export const sendDeleteUniversityReviewReactionRequest = async (reviewId: number, 
  body: AddUniversityReviewReactionRequest):
  Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/universityReview/${reviewId}/reaction`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
  return await sendRequest(uri, request)
}