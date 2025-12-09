import type { PageResponse } from "../common/PageResponse";

export type GetUserByExchangeResponse = PageResponse<{
  id: number;
  nick: string;
  exchange: {
    id: null;
    timeRange: {
      startedAt: string;
      endAt: string;
    };
    university: {
      id: number;
      nativeName: string;
      englishName: string | null;
      city: {
        id: number;
        name: string;
        country: {
          id: number;
          name: string;
        };
      };
    };
    universityMajor: {
      id: number;
      name: string;
    };
  };
}>;
