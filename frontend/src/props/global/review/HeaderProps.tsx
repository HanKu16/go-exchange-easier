export type HeaderProps = {
  author: {
    id: number;
    nick: string;
  };
  university: {
    id: number;
    englishName?: string;
    nativeName: string;
  };
  starRating: number;
  createdAt: string;
}