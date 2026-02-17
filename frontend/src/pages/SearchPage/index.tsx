import { useEffect, useState } from "react";
import { Box, Container, Pagination } from "@mui/material";
import Navbar from "../../components/Navbar";
import type { Country } from "../../types/Country";
import { sendGetCountriesRequest } from "../../utils/api/country";
import LoadingContent from "../../components/LoadingContent";
import ContentLoadError from "../../components/ContentLoadError";
import ContentEmpty from "../../components/ContentEmpty";
import PageHeaders from "./PageHeaders";
import type { SearchEntity, SearchResult } from "./types";
import SearchEntitiesOptions from "./SearchEntitiesOptions";
import UniversitySearchSection from "./UniversitySearchSection";
import UserSearchSection from "./UserSearchSection";

const SearchPage = () => {
  const [currentSearchEntity, setCurrentSearchEntity] =
    useState<SearchEntity>("user");
  const [countries, setCountries] = useState<Country[]>([]);
  const [searchResult, setSearchResult] = useState<SearchResult>({
    status: "fetchingWasNotStarted",
    resultComponent: undefined,
    totalNumberOfPages: undefined,
  });
  const [currentPage, setCurrentPage] = useState<number | undefined>(undefined);

  const handleEntityChange = (newEntity: SearchEntity) => {
    setCurrentSearchEntity(newEntity);
    setSearchResult({
      status: "fetchingWasNotStarted",
      resultComponent: undefined,
      totalNumberOfPages: undefined,
    });
    setCurrentPage(undefined);
  };

  const resetSearchResult = () => {
    setSearchResult({
      status: "fetchingWasNotStarted",
      resultComponent: undefined,
      totalNumberOfPages: undefined,
    });
    setCurrentPage(undefined);
  };

  const getCountries = async () => {
    const result = await sendGetCountriesRequest();
    if (result.isSuccess) {
      const allCountries: Country[] = result.data.content.map((c) => ({
        id: c.id,
        name: c.englishName,
        flagUrl: c.flagUrl,
      }));
      setCountries(allCountries);
    }
  };

  const getSearchResult = () => {
    switch (searchResult.status) {
      case "success":
        if (searchResult.resultComponent != undefined) {
          return searchResult.resultComponent;
        }
        return (
          <ContentEmpty
            title="No records found"
            subheader="We don't have records that fulfill your filters."
          />
        );
      case "loading":
        return <LoadingContent title="Searching..." />;
      case "connectionError":
        return (
          <ContentLoadError
            title="Connection error"
            subheader="An error occurred during search."
          />
        );
      case "serverError":
        return (
          <ContentLoadError
            title="Connection error"
            subheader="An error occurred during search."
          />
        );
      case "fetchingWasNotStarted":
        return <></>;
    }
  };

  useEffect(() => {
    getCountries();
  }, []);

  return (
    <Box sx={{ minHeight: "100vh", backgroundColor: "#ffffff" }}>
      <Navbar />
      <Container
        maxWidth="lg"
        sx={{
          mt: 8,
          mb: 10,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <PageHeaders />
        <SearchEntitiesOptions
          currentSearchEntity={currentSearchEntity}
          setCurrentSearchEntity={handleEntityChange}
        />
        {currentSearchEntity === "user" ? (
          <UserSearchSection
            key="user-section"
            countries={countries}
            setSearchResult={setSearchResult}
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
            pageSize={10}
            resetSearchResult={resetSearchResult}
          />
        ) : (
          <UniversitySearchSection
            key="university-section"
            countries={countries}
            setSearchResult={setSearchResult}
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
            pageSize={10}
            resetSearchResult={resetSearchResult}
          />
        )}
        {getSearchResult()}
        {searchResult.totalNumberOfPages != undefined &&
          searchResult.totalNumberOfPages > 1 &&
          currentPage !== undefined && (
            <Container
              sx={{ display: "flex", justifyContent: "center", marginTop: 3 }}
            >
              <Pagination
                count={searchResult.totalNumberOfPages}
                page={currentPage + 1}
                showFirstButton
                showLastButton
                onChange={(_, value) => setCurrentPage(value - 1)}
              />
            </Container>
          )}
      </Container>
    </Box>
  );
};

export default SearchPage;
