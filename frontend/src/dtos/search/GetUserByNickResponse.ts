import type { PageResponse } from "../common/PageResponse";

export type GetUserByNickResponse = PageResponse<{
  id: number;
  nick: string;
  countryOfOrigin: {
    id: number;
    name: string;
  } | null;
  homeUniversity: {
    id: number;
    nativeName: string;
    englishName: string;
  } | null;
}>;
