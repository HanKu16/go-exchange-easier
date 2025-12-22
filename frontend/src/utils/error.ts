import type { ApiErrorResponse } from '../dtos/error/ApiErrorResponse'
import type { ApiErrorResponseCode } from '../dtos/error/ApiErrorResponseCode'

export const getFieldErrors = (error: ApiErrorResponse): string[] => {
  const errorFieldNames: string[] = [
    ...new Set(error.fieldErrors.map(e => e.field))
  ];
  return errorFieldNames
}

export const getGlobalErrorCodes = (error: ApiErrorResponse): 
  ApiErrorResponseCode[] => {
  const globalErrorsCodes: ApiErrorResponseCode[] = 
    error.globalErrors.map(e => e.code)
  return globalErrorsCodes
}