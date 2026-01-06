import type { GetUserProfileResponse } from "../dtos/user/GetUserProfileResponse";
import { sendRequest } from "./send-request";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import type { GetUserExchangeResponse } from "../dtos/user/GetUserExchangeResponse";
import type { UpdateUserDescriptionRequest } from "../dtos/user/UpdateUserDescriptionRequest";
import type { UpdateUserDescriptionResponse } from "../dtos/user/UpdateUserDescriptionResponse";
import type { AssignHomeUniversityRequest } from "../dtos/user/AssignHomeUniversityRequest";
import type { AssignHomeUniversityResponse } from "../dtos/user/AssignHomeUniversityResponse";
import type { UpdateUserStatusRequest } from "../dtos/user/UpdateUserStatusRequest";
import type { UpdateUserStatusResponse } from "../dtos/user/UpdateUserStatusResponse";
import type { AssignCountryOfOriginRequest } from "../dtos/user/AssignCountryOfOriginRequest";
import type { AssignCountryOfOriginResponse } from "../dtos/user/AssignCountryOfOriginResponse";
import { API_BASE_URL } from "../config/api";
import type { UniversityReviewDetails } from "../dtos/details/UniversityReviewDetails";
import type { Listing } from "../dtos/common/Listing";
import type { PageResponse } from "../dtos/common/PageResponse";
import type { UserDetails } from "../dtos/details/UserDetails";
import type { UserSummary } from "../dtos/summary/UserSummary";
import type { UniversityDetails } from "../dtos/details/UniversityDetails";

export const sendGetUserProfileRequest = async (
  userId: number | string
): Promise<
  ResponseSuccessResult<GetUserProfileResponse> | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/users/${userId}/profile`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<GetUserProfileResponse>(uri, request);
};

export const sendGetUserReviewsRequest = async (
  userId: number | string
): Promise<
  | ResponseSuccessResult<Listing<UniversityReviewDetails>>
  | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/users/${userId}/universityReviews`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<UniversityReviewDetails>>(uri, request);
};

export const sendGetUserExchangesRequest = async (
  userId: number | string
): Promise<
  ResponseSuccessResult<GetUserExchangeResponse[]> | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/users/${userId}/exchanges`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<GetUserExchangeResponse[]>(uri, request);
};

export const sendUpdateDescriptionRequest = async (
  body: UpdateUserDescriptionRequest
): Promise<
  ResponseSuccessResult<UpdateUserDescriptionResponse> | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/users/description`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest<UpdateUserDescriptionResponse>(uri, request);
};

export const sendAssignHomeUniversityRequest = async (
  body: AssignHomeUniversityRequest
): Promise<
  ResponseSuccessResult<AssignHomeUniversityResponse> | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/users/homeUniversity`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest<AssignHomeUniversityResponse>(uri, request);
};

export const sendUpdateStatusRequest = async (
  body: UpdateUserStatusRequest
): Promise<
  ResponseSuccessResult<UpdateUserStatusResponse> | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/users/status`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest<UpdateUserStatusResponse>(uri, request);
};

export const sendAssignCountryOfOriginRequest = async (
  body: AssignCountryOfOriginRequest
): Promise<
  ResponseSuccessResult<AssignCountryOfOriginResponse> | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/users/countryOfOrigin`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest<AssignCountryOfOriginResponse>(uri, request);
};

export const getSignedInUserId = (): string => {
  const userId: string | null = localStorage.getItem("userId");
  if (userId === null) {
    throw new Error("Did not found userId");
  }
  return userId;
};

export const getSignedInUserJwtToken = (): string => {
  const token: string | null = localStorage.getItem("jwtToken");
  if (token === null) {
    throw new Error("Did not found jwt token");
  }
  return token;
};

export const sendGetUsersRequest = async (
  nick: string,
  page: number,
  size: number
): Promise<
  ResponseSuccessResult<PageResponse<UserDetails>> | RepsonseFailureResult
> => {
  const params = new URLSearchParams({
    nick: nick,
    page: page.toString(),
    size: size.toString(),
  });
  const uri: string = `${API_BASE_URL}/api/users?${params.toString()}`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<PageResponse<UserDetails>>(uri, request);
};

export const sendGetFolloweesRequest = async (): Promise<
  ResponseSuccessResult<Listing<UserSummary>> | RepsonseFailureResult
> => {
  const userId = getSignedInUserId();
  const uri: string = `${API_BASE_URL}/api/users/${userId}/followees`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<UserSummary>>(uri, request);
};

export const sendGetFollowedUniversitiesRequest = async (): Promise<
  ResponseSuccessResult<Listing<UniversityDetails>> | RepsonseFailureResult
> => {
  const userId = getSignedInUserId();
  const uri: string = `${API_BASE_URL}/api/users/${userId}/followedUniversities`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<UniversityDetails>>(uri, request);
};
