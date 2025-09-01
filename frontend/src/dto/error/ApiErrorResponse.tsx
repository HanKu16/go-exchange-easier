import type { FieldErrorDetail } from "./FieldErrorDetails";
import type { GlobalErrorDetail } from "./GlobalErrorDetail";

export interface ApiErrorResponse {
  status: string;
  message: string;
  fieldErrors: FieldErrorDetail[];
  globalErrors: GlobalErrorDetail[];
}