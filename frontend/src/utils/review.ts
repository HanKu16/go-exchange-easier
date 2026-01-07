import type { AddUniversityReviewReactionRequest } from "../dtos/university-review/AddUniversityReviewReactionRequest";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import type { CreateUniversityReviewRequest } from "../dtos/university-review/CreateUniversityReviewRequest";
import type { UniversityReviewDetails } from "../dtos/details/UniversityReviewDetails";

export const sendCreateReviewRequest = async (
  body: CreateUniversityReviewRequest
): Promise<
  ResponseSuccessResult<UniversityReviewDetails> | RepsonseFailureResult
> => {
  const uri: string = `/api/universityReviews`;
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest<UniversityReviewDetails>(uri, request);
};

export const sendAddUniversityReviewReactionRequest = async (
  reviewId: number,
  body: AddUniversityReviewReactionRequest
): Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `/api/universityReviews/${reviewId}/reaction`;
  const request: RequestInit = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};

export const sendDeleteUniversityReviewReactionRequest = async (
  reviewId: number,
  body: AddUniversityReviewReactionRequest
): Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `/api/universityReviews/${reviewId}/reaction`;
  const request: RequestInit = {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};
