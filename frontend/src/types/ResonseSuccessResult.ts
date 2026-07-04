export interface ResponseSuccessResult<ResponseBodyType> {
  isSuccess: true;
  data: ResponseBodyType;
}