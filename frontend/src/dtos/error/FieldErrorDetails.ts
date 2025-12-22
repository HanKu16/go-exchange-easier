import type { ApiErrorResponseCode } from "./ApiErrorResponseCode";

export type FieldErrorDetail = {
  field: string;
  code: ApiErrorResponseCode;
  message: string;
};
