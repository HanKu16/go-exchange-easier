import type { ApiErrorResponseCode } from "./ApiErrorResponseCode";

export interface FieldErrorDetail {
  field: string;
  code: ApiErrorResponseCode;
  message: string;
}