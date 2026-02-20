import type { SimplePage } from "../../dtos/common/SimplePage";
import type { CreateMessageRequest } from "../../dtos/message/CreateMessageRequest";
import type { MessageDetails } from "../../dtos/message/MessageDetails";
import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequest } from "../send-request";

export const sendGetMessagePageRequest = async (
  roomId: string,
  page: number,
  size: number,
): Promise<
  ResponseSuccessResult<SimplePage<MessageDetails>> | ResponseFailureResult
> => {
  const url = new URL(
    `/api/chat/rooms/${roomId}/messages`,
    window.location.origin,
  );
  url.searchParams.append("page", `${page}`);
  url.searchParams.append("size", `${size}`);
  const uri = url.toString();
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest(uri, request);
};

export const sendCreateMessageRequest = async (
  roomId: string,
  body: CreateMessageRequest,
): Promise<ResponseSuccessResult<MessageDetails> | ResponseFailureResult> => {
  const uri: string = `/api/chat/rooms/${roomId}/messages`;
  const request: RequestInit = {
    method: "POST",
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};
