import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import { API_BASE_URL } from "../config/api";
import { getSignedInUserJwtToken } from "./user";
import type { CityDetails } from "../dtos/details/CityDetails";
import type { Listing } from "../dtos/common/Listing";

export const sendGetCitiesRequest = async (
  countryId: number | undefined | null
): Promise<
  ResponseSuccessResult<Listing<CityDetails>> | RepsonseFailureResult
> => {
  const url = new URL(`${API_BASE_URL}/api/cities`);
  if (countryId) {
    url.searchParams.append("countryId", `${countryId}`);
  }
  const uri = url.toString();
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<CityDetails>>(uri, request);
};
