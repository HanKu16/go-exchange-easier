import type { Listing } from "../dtos/common/Listing";
import type { PageResponse } from "../dtos/common/PageResponse";
import type { UniversityDetails } from "../dtos/details/UniversityDetails";
import type { UniversityReviewDetails } from "../dtos/details/UniversityReviewDetails";
import type { UniversityReviewCountSummary } from "../dtos/summary/UniversityReviewCountSummary";
import type { GetUniversityProfileResponse } from "../dtos/university/GetUniversityProfileResponse";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";

export const sendGetUniversitiesRequest = async (
  englishName: string | undefined | null,
  nativeName: string | undefined | null,
  cityId: number | undefined | null,
  countryId: number | undefined | null,
  page: number,
  size: number,
  sort: string
): Promise<
  ResponseSuccessResult<PageResponse<UniversityDetails>> | ResponseFailureResult
> => {
  const url = new URL(`/api/universities`, window.location.origin);
  if (englishName) url.searchParams.append("englishName", englishName);
  if (nativeName) url.searchParams.append("nativeName", nativeName);
  if (cityId) url.searchParams.append("cityId", `${cityId}`);
  if (countryId) url.searchParams.append("countryId", `${countryId}`);
  url.searchParams.append("page", `${page}`);
  url.searchParams.append("size", `${size}`);
  url.searchParams.append("sort", sort);
  const uri = url.toString();
  const request: RequestInit = {
    method: "GET",
    // headers: {
    //   "Content-Type": "application/json",
    // },
  };
  return await sendRequest<PageResponse<UniversityDetails>>(uri, request);
};

export const sendGetUniversityProfileRequest = async (
  universityId: number | string
): Promise<
  ResponseSuccessResult<GetUniversityProfileResponse> | ResponseFailureResult
> => {
  const uri: string = `/api/universities/${universityId}/profile`;
  const request: RequestInit = {
    method: "GET",
    // headers: {
    //   "Content-Type": "application/json",
    // },
  };
  return await sendRequest<GetUniversityProfileResponse>(uri, request);
};

export const sendGetUniversityReviewsRequest = async (
  universityId: number | string,
  page: number,
  size: number
): Promise<
  | ResponseSuccessResult<Listing<UniversityReviewDetails>>
  | ResponseFailureResult
> => {
  const params = { page: `${page}`, size: `${size}` };
  const searchParams = new URLSearchParams(params).toString();
  const uri: string = `/api/universities/${universityId}/reviews?${searchParams}`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<Listing<UniversityReviewDetails>>(uri, request);
};

export const sendGetReviewsCountRequest = async (
  universityId: number | string
): Promise<
  ResponseSuccessResult<UniversityReviewCountSummary> | ResponseFailureResult
> => {
  const uri: string = `/api/universities/${universityId}/reviews/count`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<UniversityReviewCountSummary>(uri, request);
};
