import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";

export const sendFollowUserRequest = async (
  followeeId: number | string
): Promise<ResponseSuccessResult<void> | ResponseFailureResult> => {
  const uri: string = `/api/users/${followeeId}/follow`;
  const request: RequestInit = {
    method: "POST",
  };
  return await sendRequest(uri, request);
};

export const sendUnfollowUserRequest = async (
  followeeId: number | string
): Promise<ResponseSuccessResult<void> | ResponseFailureResult> => {
  const uri: string = `/api/users/${followeeId}/follow`;
  const request: RequestInit = {
    method: "DELETE",
  };
  return await sendRequest(uri, request);
};

export const sendFollowUniversityRequest = async (
  universityId: number | string
): Promise<ResponseSuccessResult<void> | ResponseFailureResult> => {
  const uri: string = `/api/universities/${universityId}/follow`;
  const request: RequestInit = {
    method: "POST",
  };
  return await sendRequest(uri, request);
};

export const sendUnfollowUniversityRequest = async (
  universityId: number | string
): Promise<ResponseSuccessResult<void> | ResponseFailureResult> => {
  const uri: string = `/api/universities/${universityId}/follow`;
  const request: RequestInit = {
    method: "DELETE",
  };
  return await sendRequest(uri, request);
};
