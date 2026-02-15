import type { UserProfileDetails } from "../dtos/user/UserProfileDetails";
import { sendRequest } from "./send-request";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../types/ResponseFailureResult";
import type { UpdateUserDescriptionRequest } from "../dtos/user/description/UpdateUserDescriptionRequest";
import type { UserDescriptionSummary } from "../dtos/user/description/UserDescriptionSummary";
import type { AssignHomeUniversityRequest } from "../dtos/user/AssignHomeUniversityRequest";
import type { UpdateUserStatusRequest } from "../dtos/user/status/UpdateUserStatusRequest";
import type { AssignCountryOfOriginRequest } from "../dtos/user/AssignCountryOfOriginRequest";
import type { UniversityReviewDetails } from "../dtos/university/review/UniversityReviewDetails";
import type { Listing } from "../dtos/common/Listing";
import type { PageResponse } from "../dtos/common/PageResponse";
import type { UserDetails } from "../dtos/user/UserDetails";
import type { UniversityDetails } from "../dtos/university/UniversityDetails";
import type { AvatarUrlSummary } from "../dtos/user/avatar/AvatarUrlSummary";
import type { UserWithAvatarSummary } from "../dtos/user/UserWithAvatarSummary";
import type { UserStatusSummary } from "../dtos/user/status/UserStatusSummary";
import type { CountrySummary } from "../dtos/location/CountrySummary";
import type { UniversitySummary } from "../dtos/university/UniversitySummary";

export const sendGetUserProfileRequest = async (
  userId: number | string,
): Promise<
  ResponseSuccessResult<UserProfileDetails> | ResponseFailureResult
> => {
  const uri: string = `/api/users/${userId}/profile`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<UserProfileDetails>(uri, request);
};

export const sendGetUserReviewsRequest = async (
  userId: number | string,
): Promise<
  | ResponseSuccessResult<Listing<UniversityReviewDetails>>
  | ResponseFailureResult
> => {
  const uri: string = `/api/users/${userId}/universityReviews`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<Listing<UniversityReviewDetails>>(uri, request);
};

export const sendUpdateDescriptionRequest = async (
  body: UpdateUserDescriptionRequest,
): Promise<
  ResponseSuccessResult<UserDescriptionSummary> | ResponseFailureResult
> => {
  const uri: string = `/api/users/description`;
  const request: RequestInit = {
    method: "PATCH",
    body: JSON.stringify(body),
  };
  return await sendRequest<UserDescriptionSummary>(uri, request);
};

export const sendAssignHomeUniversityRequest = async (
  body: AssignHomeUniversityRequest,
): Promise<
  ResponseSuccessResult<UniversitySummary> | ResponseFailureResult
> => {
  const uri: string = `/api/users/homeUniversity`;
  const request: RequestInit = {
    method: "PATCH",
    body: JSON.stringify(body),
  };
  return await sendRequest<UniversitySummary>(uri, request);
};

export const sendUpdateStatusRequest = async (
  body: UpdateUserStatusRequest,
): Promise<
  ResponseSuccessResult<UserStatusSummary> | ResponseFailureResult
> => {
  const uri: string = `/api/users/status`;
  const request: RequestInit = {
    method: "PATCH",
    body: JSON.stringify(body),
  };
  return await sendRequest<UserStatusSummary>(uri, request);
};

export const sendAssignCountryOfOriginRequest = async (
  body: AssignCountryOfOriginRequest,
): Promise<ResponseSuccessResult<CountrySummary> | ResponseFailureResult> => {
  const uri: string = `/api/users/countryOfOrigin`;
  const request: RequestInit = {
    method: "PATCH",
    body: JSON.stringify(body),
  };
  return await sendRequest<CountrySummary>(uri, request);
};

export const sendGetUsersRequest = async (
  nick: string,
  page: number,
  size: number,
): Promise<
  ResponseSuccessResult<PageResponse<UserDetails>> | ResponseFailureResult
> => {
  const params = new URLSearchParams({
    nick: nick,
    page: page.toString(),
    size: size.toString(),
  });
  const uri: string = `/api/users?${params.toString()}`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<PageResponse<UserDetails>>(uri, request);
};

export const sendGetFolloweesRequest = async (
  userId: number,
): Promise<
  ResponseSuccessResult<Listing<UserWithAvatarSummary>> | ResponseFailureResult
> => {
  const uri: string = `/api/users/${userId}/followees`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<Listing<UserWithAvatarSummary>>(uri, request);
};

export const sendGetFollowedUniversitiesRequest = async (
  userId: number,
): Promise<
  ResponseSuccessResult<Listing<UniversityDetails>> | ResponseFailureResult
> => {
  const uri: string = `/api/users/${userId}/followedUniversities`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<Listing<UniversityDetails>>(uri, request);
};

export const sendGetMeRequest = async (): Promise<
  ResponseSuccessResult<UserWithAvatarSummary> | ResponseFailureResult
> => {
  const uri: string = `/api/users/me`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<UserWithAvatarSummary>(uri, request);
};

export const sendUploadAvatarRequest = async (
  avatarImage: FormData,
): Promise<ResponseSuccessResult<AvatarUrlSummary> | ResponseFailureResult> => {
  const uri: string = `/api/users/avatar`;
  const request: RequestInit = {
    method: "POST",
    body: avatarImage,
  };
  return await sendRequest<AvatarUrlSummary>(uri, request);
};

export const sendDeleteAvatarRequest = async (): Promise<
  ResponseSuccessResult<AvatarUrlSummary> | ResponseFailureResult
> => {
  const uri: string = `/api/users/avatar`;
  const request: RequestInit = {
    method: "DELETE",
  };
  return await sendRequest<AvatarUrlSummary>(uri, request);
};
