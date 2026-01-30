import type { FieldOfStudySummary } from "../../dtos/field-of-study/FieldOfStudySummary";
import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequest } from "../send-request";
import type { Listing } from "../../dtos/common/Listing";

export const sendGetFieldsOfStudiesRequest = async (): Promise<
  ResponseSuccessResult<Listing<FieldOfStudySummary>> | ResponseFailureResult
> => {
  const uri: string = `/api/universityMajors`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<Listing<FieldOfStudySummary>>(uri, request);
};
