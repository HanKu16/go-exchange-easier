import type { ReactNode } from "react";
import type { Country } from "../../types/Country";
import type { DataFetchStatus } from "../../types/DataFetchStatus";
import type { FieldOfStudySummary } from "../../dtos/field-of-study/FieldOfStudySummary";

export type UniversityFilterState = {
  englishName: string | null;
  nativeName: string | null;
  countryId: number | null;
  cityId: number | null;
};

export type UserFilterState = {
  countryId: number | null;
  cityId: number | null;
  universityId: number | null;
  majorId: number | null;
  minYear: number | null;
  maxYear: number | null;
};

export type UniversityFilterDrawerProps = {
  open: boolean;
  onClose: () => void;
  filters: UniversityFilterState;
  setFilters: (f: UniversityFilterState) => void;
  onApply: () => void;
  clearFilters: () => void;
  countries: Country[];
  resetSearchResult: () => void;
};

export type UserFilterDrawerProps = {
  open: boolean;
  onClose: () => void;
  filters: UserFilterState;
  setFilters: (f: UserFilterState) => void;
  countries: Country[];
  resetSearchResult: () => void;
};

export type SearchEntity = "user" | "university";

export type SearchResult = {
  status: DataFetchStatus | "fetchingWasNotStarted";
  resultComponent: ReactNode | undefined;
  totalNumberOfPages: number | undefined;
};

export type SearchSectionProps = {
  countries: Country[];
  setSearchResult: (searchResult: SearchResult) => void;
  currentPage: number | undefined;
  setCurrentPage: (currentPage: number) => void;
  pageSize: number;
  resetSearchResult: () => void;
};

export type SearchResultFetchStatus = DataFetchStatus | "fetchingWasNotStarted";

export type SearchMode = "simple" | "filters";

export type Major = FieldOfStudySummary;
