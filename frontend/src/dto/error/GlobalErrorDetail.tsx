import type { ApiErrorResponseCode } from "./ApiErrorResponseCode";

export interface GlobalErrorDetail {
  code: ApiErrorResponseCode;
  message: string;
}