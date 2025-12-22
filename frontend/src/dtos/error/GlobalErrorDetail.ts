import type { ApiErrorResponseCode } from "./ApiErrorResponseCode";

export type GlobalErrorDetail = {
  code: ApiErrorResponseCode;
  message: string;
};
