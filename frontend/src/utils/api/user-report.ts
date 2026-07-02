import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequest } from "../send-request";
import type { CreateUserReport } from "../../dtos/report/user/CreateUserReport";
import type { UserReportSummary } from "../../dtos/report/user/UserReportSummary";


export const sendCreateUserReportRequest = async (
  reportedUserId: number,
  body: CreateUserReport,
): Promise<ResponseSuccessResult<UserReportSummary> | ResponseFailureResult> => {
  const uri: string = `/api/reports/users/${reportedUserId}`;
  const request: RequestInit = {
    method: "POST",
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};
