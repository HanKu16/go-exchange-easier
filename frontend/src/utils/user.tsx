import type { UserRegistrationRequest } from "../dto/user/UserRegistrationRequest"
import type { UserRegistrationResponse } from "../dto/user/UserRegistrationResponse"
import type { ApiErrorResponse } from "../dto/error/ApiErrorResponse";

type UserRegistrationSuccessResult = {
  isSuccess: true;
  data: UserRegistrationResponse;
};

type UserRegistrationErrorResult = {
  isSuccess: false;
  error: ApiErrorResponse;
};

export type UserRegistrationResult = 
  UserRegistrationSuccessResult | 
  UserRegistrationErrorResult;

export const sendUserRegistrationRequest = async (body: UserRegistrationRequest): 
  Promise<UserRegistrationResult> => {
  const uri: string = "http://localhost:8080/api/auth/register"
  const request: RequestInit = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  }

  try {
    const response = await fetch(uri, request)
    if (response.ok) {
      const data: UserRegistrationResponse = await response.json()
      const result: UserRegistrationSuccessResult = {
        isSuccess: true,
        data: data
      }
      return result
    } else {
      const error: ApiErrorResponse = await response.json()
      const result: UserRegistrationErrorResult = {
        isSuccess: false,
        error: error
      }
      return result
    }
  } catch (error) {
    const result: UserRegistrationErrorResult = {
      isSuccess: false,
      error: { 
        status: "INTERNAL_SERVER_ERROR",
        message: "An unexepected error occured",
        fieldErrors: [],
        globalErrors: [{
          code: "InternalError",
          message: "An unexepected error occured"
        }]
      }
    }
    return result
  }
}