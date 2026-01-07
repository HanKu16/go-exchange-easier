import type { ApiErrorResponse } from "../dtos/error/ApiErrorResponse";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { RepsonseFailureResult } from "../types/ResponseFailureResult";

export async function sendRequest<ResponseSuccessBody>(
  uri: string,
  request: RequestInit
): Promise<ResponseSuccessResult<ResponseSuccessBody> | RepsonseFailureResult> {
  try {
    const response = await fetch(uri, { ...request, credentials: "include" });
    if (response.status === 403) {
      window.location.href = "/login";
    }
    if (response.ok) {
      if (response.status === 204) {
        const result: ResponseSuccessResult<ResponseSuccessBody> = {
          isSuccess: true,
          data: null as ResponseSuccessBody,
        };
        return result;
      }
      const data = (await response.json()) as ResponseSuccessBody;
      const result: ResponseSuccessResult<ResponseSuccessBody> = {
        isSuccess: true,
        data: data,
      };
      return result;
    } else {
      const error: ApiErrorResponse = await response.json();
      const result: RepsonseFailureResult = {
        isSuccess: false,
        error: error,
      };
      return result;
    }
  } catch (error) {
    if (error instanceof TypeError && error.message === "Failed to fetch") {
      const result: RepsonseFailureResult = {
        isSuccess: false,
        error: {
          status: "SERVICE_UNAVAILABLE",
          message: "Could not connect to the server. Service unavailable.",
          fieldErrors: [],
          globalErrors: [
            {
              code: "ConnectionError",
              message: "Failed to connect to the backend server.",
            },
          ],
        },
      };
      return result;
    }
    const result: RepsonseFailureResult = {
      isSuccess: false,
      error: {
        status: "INTERNAL_SERVER_ERROR",
        message: "An unexepected error occured.",
        fieldErrors: [],
        globalErrors: [
          {
            code: "InternalError",
            message: "An unexepected error occured.",
          },
        ],
      },
    };
    return result;
  }
}
