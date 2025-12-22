// import type { GetCountryResponse } from "../dtos/country/GetCountryResponse";
// import type { GetUniversityResponse } from "../dtos/university/GetUniversityResponse";
// import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
// import type { RepsonseFailureResult } from "../types/ResponseFailureResult";
// import { sendRequest } from "./send-request";
// import { API_BASE_URL } from "../config/api";
// import { getSignedInUserJwtToken } from "./user";

// export const sendGetCitiesFromCountryRequest = async (
//   countryId: number
// ): Promise<
//   ResponseSuccessResult<GetCityResponse[]> | RepsonseFailureResult
// > => {
//   // const uri: string = `${API_BASE_URL}/api/countries/${countryId}/cities`
//   const jwtToken = getSignedInUserJwtToken();
//   const request: RequestInit = {
//     method: "GET",
//     headers: {
//       Authorization: `Bearer ${jwtToken}`,
//       "Content-Type": "application/json",
//     },
//   };
//   return await sendRequest<GetCountryResponse[]>(uri, request);
// };
