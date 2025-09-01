import type { LoginRequest } from "../dto/auth/LoginRequest"
import type { LoginResponse } from "../dto/auth/LoginResponse";
import type { ApiErrorResponse } from "../dto/error/ApiErrorResponse";

type LoginSuccessResult = {
  isSuccess: true;
  data: LoginResponse;
};

type LoginErrorResult = {
  isSuccess: false;
  error: ApiErrorResponse | { message: string };
};

export type LoginResult = LoginSuccessResult | LoginErrorResult;

export const sendLoginRequest = async (body: LoginRequest) => {
    const uri: string = "http://localhost:8080/api/auth/login"
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
      const data: LoginResponse = await response.json()
      const result: LoginSuccessResult = {
        isSuccess: true,
        data
      }
      return result
    } else {
      const data: ApiErrorResponse = await response.json()
      const result: LoginErrorResult = {
        isSuccess: false,
        error: data
      }
      return result
    }
  } catch (error) {
      const result: LoginErrorResult = {
        isSuccess: false,
        error: { message: "An error occured: " + error }
      }
      return result
  }
}