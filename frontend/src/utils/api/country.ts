import type { ResponseSuccessResult } from "../../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../../types/ResponseFailureResult";
import { sendRequest } from "../send-request";
import type { CountrySummary } from "../../dtos/location/CountrySummary";
import type { Listing } from "../../dtos/common/Listing";

export const sendGetCountriesRequest = async (): Promise<
  ResponseSuccessResult<Listing<CountrySummary>> | ResponseFailureResult
> => {
  const uri: string = `/api/countries`;
  const request: RequestInit = {
    method: "GET",
  };
  return await sendRequest<Listing<CountrySummary>>(uri, request);
};
