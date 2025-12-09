import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import type { GetUserByNickResponse } from "../dtos/search/GetUserByNickResponse";
import { API_BASE_URL } from "../config/api";
import { getSignedInUserJwtToken } from "./user";
import { sendRequest } from "./send-request";

export const sendGetUserByNickRequest = async (
  nick: string,
  page: number,
  size: number
): Promise<
  ResponseSuccessResult<GetUserByNickResponse> | RepsonseFailureResult
> => {
  const params = new URLSearchParams({
    nick: nick,
    page: page.toString(),
    size: size.toString(),
  });
  const uri: string = `${API_BASE_URL}/api/search/users/nick?${params.toString()}`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<GetUserByNickResponse>(uri, request);
};

export const sendGetUserByExchangeRequest = async (
  universityId: number | null,
  cityId: number | null,
  countryId: number | null,
  majorId: number | null,
  startDate: string | null,
  endDate: string | null,
  page: number,
  size: number
): Promise<
  ResponseSuccessResult<GetUserByNickResponse> | RepsonseFailureResult
> => {
  const params = new URLSearchParams({
    universityId: universityId !== null ? universityId.toString() : "",
    cityId: cityId !== null ? cityId.toString() : "",
    countryId: countryId !== null ? countryId.toString() : "",
    majorId: majorId !== null ? majorId.toString() : "",
    startDate: startDate !== null ? startDate : "",
    endDate: endDate !== null ? endDate : "",
    page: page.toString(),
    size: size.toString(),
    sort: "endAt,DESC",
  });
  const uri: string = `${API_BASE_URL}/api/search/users/exchanges?${params.toString()}`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<GetUserByNickResponse>(uri, request);
};
