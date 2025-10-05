import type { GetUniversityMajorResponse } from '../dtos/university-major/GetUniversityMajorResponse'
import type { ResponseSuccessResult } from '../types/ResonseSuccessResult'
import type { RepsonseFailureResult } from '../types/ResponseFailureResult'
import { sendRequest } from './send-request'
import { API_BASE_URL } from '../config/api'

export const sendGetUniversityMajorsRequest = async () :
  Promise<ResponseSuccessResult<GetUniversityMajorResponse[]> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/universityMajors`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest<GetUniversityMajorResponse[]>(uri, request)
}