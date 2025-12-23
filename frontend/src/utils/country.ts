import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import { API_BASE_URL } from "../config/api";
import { getSignedInUserJwtToken } from "./user";
import type { CountryDetails } from "../dtos/details/CountryDetails";
import type { Listing } from "../dtos/common/Listing";

export const sendGetCountriesRequest = async (): Promise<
  ResponseSuccessResult<Listing<CountryDetails>> | RepsonseFailureResult
> => {
  const uri: string = `${API_BASE_URL}/api/countries`;
  const jwtToken = getSignedInUserJwtToken();
  const request: RequestInit = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwtToken}`,
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<CountryDetails>>(uri, request);
};
