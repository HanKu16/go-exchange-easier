import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import type { CityDetails } from "../dtos/details/CityDetails";
import type { Listing } from "../dtos/common/Listing";

export const sendGetCitiesRequest = async (
  countryId: number | undefined | null
): Promise<
  ResponseSuccessResult<Listing<CityDetails>> | RepsonseFailureResult
> => {
  const url = new URL(`/api/cities`, window.location.origin);
  if (countryId) {
    url.searchParams.append("countryId", `${countryId}`);
  }
  const uri = url.toString();
  const request: RequestInit = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<CityDetails>>(uri, request);
};
