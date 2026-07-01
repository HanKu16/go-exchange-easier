import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequest } from "../send-request";
import type { ReportDictionary } from "../../dtos/report/ReportDictionary";

export const sendGetReportDictionaryRequest = async (): Promise<
  ResponseSuccessResult<ReportDictionary> | ResponseFailureResult
> => {
  const uri: string = `/api/reports/dictionary`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<ReportDictionary>(uri, request);
};
