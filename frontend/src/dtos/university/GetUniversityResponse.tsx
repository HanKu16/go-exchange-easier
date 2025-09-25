export type GetUniversityResponse = {
  id: number;
  nativeName: string;
  englishName: string | null;
  city: {
    id: number;
    name: string;
  };
}
