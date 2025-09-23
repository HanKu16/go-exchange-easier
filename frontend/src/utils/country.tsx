import type { GetCountryResponse } from "../dtos/country/GetCountryResponse"
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult"
import type { RepsonseFailureResult } from "../types/ResponseFailureResult"
import { sendRequest } from "./send-request"

export const sendGetCountriesRequest = async () :
  Promise<ResponseSuccessResult<GetCountryResponse[]> | RepsonseFailureResult> => {
  const uri: string = `http://localhost:8080/api/countries`
  const jwtToken = localStorage.getItem('jwtToken')
  const request: RequestInit = {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json',
    }
  }
  return await sendRequest<GetCountryResponse[]>(uri, request)
}