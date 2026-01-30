import type { AddUniversityReviewReactionRequest } from "../../dtos/university/review/AddUniversityReviewReactionRequest";
import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequest } from "../send-request";
import type { CreateUniversityReviewRequest } from "../../dtos/university/review/CreateUniversityReviewRequest";
import type { UniversityReviewDetails } from "../../dtos/university/review/UniversityReviewDetails";

export const sendCreateReviewRequest = async (
  body: CreateUniversityReviewRequest,
): Promise<
  ResponseSuccessResult<UniversityReviewDetails> | ResponseFailureResult
> => {
  const uri: string = `/api/universityReviews`;
  const request: RequestInit = {
    method: "POST",
    body: JSON.stringify(body),
  };
  return await sendRequest<UniversityReviewDetails>(uri, request);
};

export const sendAddUniversityReviewReactionRequest = async (
  reviewId: number,
  body: AddUniversityReviewReactionRequest,
): Promise<ResponseSuccessResult<void> | ResponseFailureResult> => {
  const uri: string = `/api/universityReviews/${reviewId}/reaction`;
  const request: RequestInit = {
    method: "PUT",
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};

export const sendDeleteUniversityReviewReactionRequest = async (
  reviewId: number,
  body: AddUniversityReviewReactionRequest,
): Promise<ResponseSuccessResult<void> | ResponseFailureResult> => {
  const uri: string = `/api/universityReviews/${reviewId}/reaction`;
  const request: RequestInit = {
    method: "DELETE",
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};
