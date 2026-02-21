import type { SimplePage } from "../../dtos/common/SimplePage";
import type { CreateRoomRequest } from "../../dtos/room/CreateRoomRequest";
import type { RoomDetails } from "../../dtos/room/RoomDetails";
import type { RoomSummary } from "../../dtos/room/RoomSummary";
import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequest } from "../send-request";

export const sendGetRoomPageRequest = async (
  page: number,
  size: number,
): Promise<
  ResponseSuccessResult<SimplePage<RoomSummary>> | ResponseFailureResult
> => {
  const url = new URL(`/api/chat/rooms`, window.location.origin);
  url.searchParams.append("page", `${page}`);
  url.searchParams.append("size", `${size}`);
  const uri = url.toString();
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest(uri, request);
};

export const sendGetOrCreateRoomRequest = async (
  body: CreateRoomRequest,
): Promise<ResponseSuccessResult<RoomDetails> | ResponseFailureResult> => {
  const uri: string = `/api/chat/rooms`;
  const request: RequestInit = {
    method: "POST",
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};

export const sendGetRoomRequest = async (
  roomId: string,
): Promise<ResponseSuccessResult<RoomDetails> | ResponseFailureResult> => {
  const uri: string = `/api/chat/rooms/${roomId}`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest(uri, request);
};
