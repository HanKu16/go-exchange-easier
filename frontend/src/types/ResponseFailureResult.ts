import type { ApiErrorResponse } from "../dtos/error/ApiErrorResponse";

export type ResponseFailureResult = {
  isSuccess: false;
  error: ApiErrorResponse;
};
