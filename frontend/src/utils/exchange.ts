import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import type { CreateExchangeRequest } from "../dtos/exchange/CreateExchangeRequest";
import type { CreateExchangeResponse } from "../dtos/exchange/CreateExchangeResponse";
import { API_BASE_URL } from "../config/api";
import { getSignedInUserJwtToken } from "./user";
import type { ExchangeDetails } from "../dtos/details/ExchangeDetails";
import type { PageResponse } from "../dtos/common/PageResponse";

export const sendGetExchangesRequest = async (
  countryId: number | undefined | null,
  universityId: number | undefined | null,
  cityId: number | undefined | null,
  majorId: number | undefined | null,
  startDate: string | undefined | null,
  endDate: string | undefined | null
): Promise<
  ResponseSuccessResult<PageResponse<ExchangeDetails>> | RepsonseFailureResult
> => {
  const url = new URL(`${API_BASE_URL}/api/exchanges`);
  if (countryId) url.searchParams.append("countryId", `${countryId}`);
  if (universityId) url.searchParams.append("universityId", `${universityId}`);
  if (cityId) url.searchParams.append("cityId", `${cityId}`);
  if (majorId) url.searchParams.append("majorId", `${majorId}`);
  if (startDate) url.searchParams.append("startDate", startDate);
  if (endDate) url.searchParams.append("endDate", endDate);
  url.searchParams.append("sort", "endDate,desc");
  const uri = url.toString();
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<PageResponse<ExchangeDetails>>(uri, request);
};

export const sendCreateExchangeRequest = async (
  body: CreateExchangeRequest
): Promise<
  ResponseSuccessResult<CreateExchangeResponse> | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/exchanges`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "POST",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  };
  return await sendRequest(uri, request);
};

export const sendDeleteExchangeRequest = async (
  exchangeId: number
): Promise<ResponseSuccessResult<null> | RepsonseFailureResult> => {
  const uri: string = `${API_BASE_URL}/api/exchanges/${exchangeId}`;
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
