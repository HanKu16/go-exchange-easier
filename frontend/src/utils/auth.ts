import type { LoginRequest } from '../dtos/auth/LoginRequest'
import type { LoginResponse } from '../dtos/auth/LoginResponse'
import type { ResponseSuccessResult } from '../types/ResonseSuccessResult'
import type { RepsonseFailureResult } from '../types/ResponseFailureResult'
import { sendRequest } from './send-request'
import { API_BASE_URL } from '../config/api'

export const sendLoginRequest = async (body: LoginRequest) :
  Promise<ResponseSuccessResult<LoginResponse> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/auth/login`
  const request: RequestInit = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
  return await sendRequest<LoginResponse>(uri, request)
}
