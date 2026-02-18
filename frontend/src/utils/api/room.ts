import type { SimplePage } from "../../dtos/common/SimplePage";
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
