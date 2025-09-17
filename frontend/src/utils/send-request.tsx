import type { ApiErrorResponse } from "../dtos/error/ApiErrorResponse";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";

export async function sendRequest<ResponseSuccessBody>(uri: string, request: RequestInit): 
  Promise<ResponseSuccessResult<ResponseSuccessBody> | RepsonseFailureResult>  {
  try {
    const response = await fetch(uri, request)
    if (response.ok) {
      const data = (await response.json()) as ResponseSuccessBody;
      const result: ResponseSuccessResult<ResponseSuccessBody> = {
        isSuccess: true,
        data: data
      }
      return result
    } else {
      const error: ApiErrorResponse = await response.json()
      const result: RepsonseFailureResult = {
        isSuccess: false,
        error: error
      }
      return result
    }
  } catch (error) {
    const result: RepsonseFailureResult = {
      isSuccess: false,
      error: { 
        status: "INTERNAL_SERVER_ERROR",
        message: "An unexepected error occured",
        fieldErrors: [],
        globalErrors: [{
          code: "InternalError",
          message: "An unexepected error occured"
        }]
      }
    }
    return result
  }
}
