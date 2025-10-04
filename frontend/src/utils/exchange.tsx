import type { ResponseSuccessResult } from '../types/ResonseSuccessResult'
import type { RepsonseFailureResult } from '../types/ResponseFailureResult'
import { sendRequest } from './send-request'
import type { CreateExchangeRequest } from '../dtos/exchange/CreateExchangeRequest'
import type { CreateExchangeResponse } from '../dtos/exchange/CreateExchangeResponse'

export const sendCreateExchangeRequest =  async (body: CreateExchangeRequest):
  Promise<ResponseSuccessResult<CreateExchangeResponse> | RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/exchange`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
  return await sendRequest(uri, request)
}

export const sendDeleteExchangeRequest =  async (exchangeId: number):
  Promise<ResponseSuccessResult<null> | RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/exchange/${exchangeId}`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    },
  }
  return await sendRequest(uri, request)
}