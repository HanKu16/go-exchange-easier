import type { ApiErrorResponse } from "../dtos/error/ApiErrorResponse";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../types/ResponseFailureResult";
import { sendRefreshRequest } from "./auth";
import { getDeviceId } from "./device";

export async function sendRequest<ResponseSuccessBody>(
  uri: string,
  request: RequestInit
): Promise<ResponseSuccessResult<ResponseSuccessBody> | ResponseFailureResult> {
  try {
    const callToApi = async () => {
      return await fetch(uri, {
        ...request,
        headers: {
          ...(request.headers || {}),
          "Content-Type": "application/json",
          "X-Device-Id": getDeviceId(),
        },
        credentials: "include",
      });
    };
    let response = await callToApi();
    if (response.status === 403 || response.status == 401) {
      const refreshResponse = await sendRefreshRequest();
      if (!refreshResponse.isSuccess) {
        window.location.href = "/login";
        return {
          isSuccess: false,
          error: {
            status: "UNAUTHORIZED",
            message: "Session expired. Please login again.",
            fieldErrors: [],
            globalErrors: [],
          },
        };
      } else {
        response = await callToApi();
      }
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
      const result: ResponseFailureResult = {
        isSuccess: false,
        error: error,
      };
      return result;
    }
  } catch (error) {
    if (error instanceof TypeError && error.message === "Failed to fetch") {
      const result: ResponseFailureResult = {
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
    const result: ResponseFailureResult = {
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

export async function sendRequestWithoutRefresh<ResponseSuccessBody>(
  uri: string,
  request: RequestInit
): Promise<ResponseSuccessResult<ResponseSuccessBody> | ResponseFailureResult> {
  try {
    let response = await fetch(uri, {
      ...request,
      headers: {
        ...(request.headers || {}),
        "Content-Type": "application/json",
        "X-Device-Id": getDeviceId(),
      },
      credentials: "include",
    });
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
      const result: ResponseFailureResult = {
        isSuccess: false,
        error: error,
      };
      return result;
    }
  } catch (error) {
    if (error instanceof TypeError && error.message === "Failed to fetch") {
      const result: ResponseFailureResult = {
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
    const result: ResponseFailureResult = {
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
