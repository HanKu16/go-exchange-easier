import type { ApiErrorResponse } from "../dtos/error/ApiErrorResponse";

export interface RepsonseFailureResult {
  isSuccess: false;
  error: ApiErrorResponse 
}