import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequest } from "../send-request";
import type { CreateChatReportRequest } from "../../dtos/report/chat/CreateChatReportRequest";
import type { ChatReportSummary } from "../../dtos/report/chat/ChatReportSummary";


export const sendCreateChatReportRequest = async (
  body: CreateChatReportRequest
): Promise<ResponseSuccessResult<ChatReportSummary> | ResponseFailureResult> => {
  const uri: string = `/api/reports/chats`;
  const request: RequestInit = {
    method: "POST",
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};
