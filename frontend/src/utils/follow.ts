import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import { getSignedInUserJwtToken } from "./user";

export const sendFollowUserRequest = async (
  followeeId: number | string
): Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `/api/users/${followeeId}/follow`;
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  };
  return await sendRequest(uri, request);
};

export const sendUnfollowUserRequest = async (
  followeeId: number | string
): Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `/api/users/${followeeId}/follow`;
  const request: RequestInit = {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  };
  return await sendRequest(uri, request);
};

export const sendFollowUniversityRequest = async (
  universityId: number | string
): Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `/api/universities/${universityId}/follow`;
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  };
  return await sendRequest(uri, request);
};

export const sendUnfollowUniversityRequest = async (
  universityId: number | string
): Promise<ResponseSuccessResult<void> | RepsonseFailureResult> => {
  const uri: string = `/api/universities/${universityId}/follow`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest(uri, request);
};
