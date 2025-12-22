export type SortInfo = {
  sorted: boolean;
  empty: boolean;
  unsorted: boolean;
};

export type PageableInfo = {
  pageNumber: number;
  pageSize: number;
  sort: SortInfo;
  offset: number;
  paged: boolean;
  unpaged: boolean;
};

export type PageResponse<T> = {
  content: T[];
  pageable: PageableInfo;
  totalElements: number;
  totalPages: number;
  last: boolean;
  size: number;
  number: number;
  sort: SortInfo;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
};
