import type { ApiErrorResponse } from "../dto/error/ApiErrorResponse";

export interface RepsonseFailureResult {
  isSuccess: false;
  error: ApiErrorResponse 
}