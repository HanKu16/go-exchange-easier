import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
import { sendRequest } from "./send-request";
import type { CountryDetails } from "../dtos/details/CountryDetails";
import type { Listing } from "../dtos/common/Listing";

export const sendGetCountriesRequest = async (): Promise<
  ResponseSuccessResult<Listing<CountryDetails>> | RepsonseFailureResult
> => {
  const uri: string = `/api/countries`;
  const request: RequestInit = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  };
  return await sendRequest<Listing<CountryDetails>>(uri, request);
};
