import type { FieldErrorDetail } from "./FieldErrorDetails";
import type { GlobalErrorDetail } from "./GlobalErrorDetail";

export type ApiErrorResponse = {
  status: string;
  message: string;
  fieldErrors: FieldErrorDetail[];
  globalErrors: GlobalErrorDetail[];
};
