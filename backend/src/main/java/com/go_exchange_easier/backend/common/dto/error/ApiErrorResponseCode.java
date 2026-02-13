package com.go_exchange_easier.backend.common.dto.error;

public enum ApiErrorResponseCode {

    ResourceNotFound,
    ReferencedResourceNotFound,
    PermissionDenied,
    AuthenticationFailed,
    InvalidToken,
    LoginAlreadyTaken,
    MailAlreadyTaken,
    InvalidRequestBody,
    ResourceAlreadyExists,
    InternalError,
    IllegalOperation,
    MissingRequestHeader,
    InvalidParameterType,
    MethodNotSupported

}
