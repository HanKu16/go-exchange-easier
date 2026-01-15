import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import type { CreateExchangeRequest } from "../dtos/exchange/CreateExchangeRequest";
import type { CreateExchangeResponse } from "../dtos/exchange/CreateExchangeResponse";
import type { ExchangeDetails } from "../dtos/details/ExchangeDetails";
import type { PageResponse } from "../dtos/common/PageResponse";

export const sendGetExchangesRequest = async (
  page: number,
  size: number,
  sort: string,
  countryId: number | undefined | null,
  universityId: number | undefined | null,
  cityId: number | undefined | null,
  majorId: number | undefined | null,
  startDate: string | undefined | null,
  endDate: string | undefined | null,
  userId: number | undefined | null
): Promise<
  ResponseSuccessResult<PageResponse<ExchangeDetails>> | ResponseFailureResult
> => {
  const url = new URL(`/api/exchanges`, window.location.origin);
  if (countryId) url.searchParams.append("countryId", `${countryId}`);
  if (universityId) url.searchParams.append("universityId", `${universityId}`);
  if (cityId) url.searchParams.append("cityId", `${cityId}`);
  if (majorId) url.searchParams.append("majorId", `${majorId}`);
  if (startDate) url.searchParams.append("startDate", startDate);
  if (endDate) url.searchParams.append("endDate", endDate);
  if (userId) url.searchParams.append("userId", `${userId}`);
  url.searchParams.append("page", `${page}`);
  url.searchParams.append("size", `${size}`);
  url.searchParams.append("sort", sort);
  const uri = url.toString();
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<PageResponse<ExchangeDetails>>(uri, request);
};

export const sendCreateExchangeRequest = async (
  body: CreateExchangeRequest
): Promise<
  ResponseSuccessResult<CreateExchangeResponse> | ResponseFailureResult
> => {
  const uri: string = `/api/exchanges`;
  const request: RequestInit = {
    method: "POST",
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};

export const sendDeleteExchangeRequest = async (
  exchangeId: number
): Promise<ResponseSuccessResult<null> | ResponseFailureResult> => {
  const uri: string = `/api/exchanges/${exchangeId}`;
  const request: RequestInit = {
    method: "DELETE",
  };
  return await sendRequest(uri, request);
};
