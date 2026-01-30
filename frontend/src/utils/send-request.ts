import type { ApiErrorResponse } from "../dtos/error/ApiErrorResponse";
import type { ResponseSuccessResult } from "../types/ResonseSuccessResult";
import type { ResponseFailureResult } from "../types/ResponseFailureResult";
import { sendRefreshRequest } from "./api/auth";
import { getDeviceId, getReadableDeviceName } from "./device";

export async function sendRequest<ResponseSuccessBody>(
  uri: string,
  request: RequestInit,
): Promise<ResponseSuccessResult<ResponseSuccessBody> | ResponseFailureResult> {
  try {
    const callToApi = async () => {
      const isFormData = request.body instanceof FormData;
      const headers: Record<string, string> = {
        ...((request.headers as Record<string, string>) || {}),
        "X-Device-Id": getDeviceId(),
        "X-Device-Name": getReadableDeviceName(),
      };
      if (!isFormData) {
        headers["Content-Type"] = "application/json";
      }
      return await fetch(uri, {
        ...request,
        headers: headers,
        credentials: "include",
      });
    };
    let response = await callToApi();
    if (response.status === 401) {
      const refreshResponse = await sendRefreshRequest();
      if (!refreshResponse.isSuccess) {
        window.dispatchEvent(new Event("auth:logout"));
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
      if (response.status >= 500) {
        return {
          isSuccess: false,
          error: {
            status: "SERVICE_UNAVAILABLE",
            message: "Server is not responding (Proxy Error).",
            fieldErrors: [],
            globalErrors: [
              {
                code: "ConnectionError",
                message: `Server responded with status ${response.status}`,
              },
            ],
          },
        };
      }
      try {
        const error: ApiErrorResponse = await response.json();
        return {
          isSuccess: false,
          error: error,
        };
      } catch (e) {
        return {
          isSuccess: false,
          error: {
            status: "INTERNAL_SERVER_ERROR",
            message: "Unknown error format received from server.",
            fieldErrors: [],
            globalErrors: [],
          },
        };
      }
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
  request: RequestInit,
): Promise<ResponseSuccessResult<ResponseSuccessBody> | ResponseFailureResult> {
  try {
    let response = await fetch(uri, {
      ...request,
      headers: {
        ...(request.headers || {}),
        "Content-Type": "application/json",
        "X-Device-Id": getDeviceId(),
        "X-Device-Name": getReadableDeviceName(),
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
