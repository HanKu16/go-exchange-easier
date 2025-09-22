export type GetUserExchangeResponse = {
  id: number;
  timeRange: {
    startedAt: string;
    endAt: string;
  };
  university: {
    id: number;
    nativeName: string;
    englishName: string;
  };
  universityMajor: {
    id: number;
    name: string;
  };
  city:{
    id: number;
    name: string;
    country: {
      id: number;
      name: string;
    };
  };
}
