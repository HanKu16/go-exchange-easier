import {
  Paper,
  Box,
  Select,
  MenuItem,
  Divider,
  InputBase,
  IconButton,
  Button,
} from "@mui/material";
import { useState, useEffect } from "react";
import SearchResultTable from "../../components/SearchResult";
import type { UniversityDetails } from "../../dtos/university/UniversityDetails";
import { sendGetUniversitiesRequest } from "../../utils/api/university";
import type {
  SearchResultFetchStatus,
  SearchSectionProps,
  UniversityFilterState,
} from "./types";
import SearchIcon from "@mui/icons-material/Search";
import UniversityFilterDrawer from "./UniversityFilterDrawer";
import FilterListIcon from "@mui/icons-material/Tune";

const UniversitySearchSection = (props: SearchSectionProps) => {
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [uniNameType, setUniNameType] = useState<"english" | "native">(
    "english",
  );
  const [searchName, setSearchName] = useState("");
  const [universities, setUniversities] = useState<UniversityDetails[]>([]);
  const [uniFilters, setUniFilters] = useState<UniversityFilterState>({
    countryId: null,
    cityId: null,
    englishName: null,
    nativeName: null,
  });
  const [universitiesFetchStatus, setUniversitiesFetchStatus] =
    useState<SearchResultFetchStatus>("fetchingWasNotStarted");
  const [totalPagesCount, setTotalPagesCount] = useState<number | undefined>(
    undefined,
  );

  const getUniverisities = async () => {
    if (props.currentPage === undefined) {
      return;
    }
    setUniversitiesFetchStatus("loading");
    const result = await sendGetUniversitiesRequest(
      uniFilters.englishName,
      uniFilters.nativeName,
      uniFilters.cityId,
      uniFilters.countryId,
      props.currentPage,
      props.pageSize,
      "englishName,asc",
    );
    if (result.isSuccess) {
      setUniversities(result.data.content);
      setTotalPagesCount(result.data.totalPages);
      setUniversitiesFetchStatus("success");
    } else {
      setUniversities([]);
      switch (result.error.status) {
        case "INTERNAL_SERVER_ERROR":
          setUniversitiesFetchStatus("serverError");
          break;
        case "SERVICE_UNAVAILABLE":
          setUniversitiesFetchStatus("connectionError");
          break;
      }
    }
  };

  useEffect(() => {
    if (props.currentPage === undefined) {
      return;
    }
    getUniverisities();
  }, [props.currentPage]);

  useEffect(() => {
    setSearchName("");
    props.resetSearchResult();
    if (uniNameType === "english") {
      setUniFilters({ ...uniFilters, nativeName: null });
    } else if (uniNameType === "native") {
      setUniFilters({ ...uniFilters, englishName: null });
    }
  }, [uniNameType]);

  useEffect(() => {
    if (universitiesFetchStatus === "success") {
      props.setSearchResult({
        status: "success",
        resultComponent: getSuccessSearchResult(),
        totalNumberOfPages: totalPagesCount,
      });
    } else {
      props.setSearchResult({
        status: universitiesFetchStatus,
        resultComponent: undefined,
        totalNumberOfPages: undefined,
      });
    }
  }, [universitiesFetchStatus]);

  const shouldShowGoToDrawerIcon = () => {
    return props.countries.length > 0;
  };

  const clearFilters = () => {
    setUniFilters({
      countryId: null,
      cityId: null,
      englishName: null,
      nativeName: null,
    });
    setSearchName("");
  };

  const getSuccessSearchResult = () => {
    if (universities.length === 0) {
      return undefined;
    }
    return (
      <SearchResultTable
        columnNames={["Native name", "English name", "City", "Country"]}
        rows={universities.map((u) => ({
          id: u.id,
          data: [
            {
              toShow: <span>{u.nativeName || ""}</span>,
              route: `/universities/${u.id}`,
            },
            {
              toShow: <span>{u.englishName}</span>,
              route: `/universities/${u.id}`,
            },
            { toShow: u.city?.name || "", route: undefined },
            {
              toShow:
                (
                  <>
                    {u.city?.country?.englishName}
                    <img
                      src={u.city?.country?.flagUrl}
                      style={{ height: "0.8rem", marginLeft: 2 }}
                      onError={(event) =>
                        (event.currentTarget.style.display = "none")
                      }
                    />
                  </>
                ) || "",
              route: undefined,
            },
          ],
        }))}
      />
    );
  };

  return (
    <Paper
      elevation={4}
      sx={{
        display: "flex",
        alignItems: "center",
        width: { xs: "95%", sm: "90%" },
        maxWidth: 800,
        borderRadius: "50px",
        border: "1px solid rgba(0,0,0,0.05)",
        boxShadow: "0px 4px 20px rgba(0,0,0,0.08)",
        p: { xs: "4px 8px", md: "8px 8px 8px 24px" },
      }}
    >
      <Box
        sx={{
          flex: 1,
          display: "flex",
          alignItems: "center",
          overflow: "hidden",
        }}
      >
        <Select
          value={uniNameType}
          onChange={(e) =>
            setUniNameType(e.target.value as "english" | "native")
          }
          disableUnderline
          variant="standard"
          sx={{
            fontWeight: 600,
            fontSize: "0.85rem",
            color: "primary.main",
            mr: { xs: 0.5, md: 1.5 },
            minWidth: { xs: 60, md: 80 },
            "& .MuiSelect-icon": { color: "primary.main" },
          }}
        >
          <MenuItem value="english">ENG</MenuItem>
          <MenuItem value="native">NAT</MenuItem>
        </Select>
        <Divider
          orientation="vertical"
          sx={{ height: 20, mr: { xs: 1, md: 2 } }}
        />
        <InputBase
          sx={{
            flex: 1,
            fontSize: { xs: "0.95rem", md: "1.1rem" },
            fontWeight: 500,
          }}
          placeholder={
            uniNameType === "english" ? "English name" : "Native name"
          }
          value={searchName}
          onChange={(e) => {
            setSearchName(e.target.value);
            if (uniNameType === "english") {
              props.resetSearchResult();
              setUniFilters({ ...uniFilters, englishName: e.target.value });
            } else {
              props.resetSearchResult();
              setUniFilters({ ...uniFilters, nativeName: e.target.value });
            }
          }}
        />
      </Box>
      {shouldShowGoToDrawerIcon() && (
        <IconButton
          onClick={() => setDrawerOpen(true)}
          sx={{
            color: "primary.main",
            mr: { xs: 0, md: 1 },
            padding: { xs: 1, md: "8px" },
          }}
        >
          <FilterListIcon />
        </IconButton>
      )}
      <IconButton
        onClick={() => props.setCurrentPage(0)}
        sx={{
          display: { xs: "flex", sm: "none" },
          bgcolor: "black",
          color: "white",
          p: 1,
          ml: 1,
          "&:hover": { bgcolor: "#333" },
        }}
      >
        <SearchIcon />
      </IconButton>
      <Button
        variant="contained"
        onClick={() => props.setCurrentPage(0)}
        sx={{
          display: { xs: "none", sm: "flex" },
          borderRadius: "50px",
          px: 4,
          py: 1.5,
          fontWeight: 700,
          bgcolor: "black",
          "&:hover": { bgcolor: "#333" },
          ml: 1,
        }}
      >
        Search
      </Button>
      <UniversityFilterDrawer
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
        filters={uniFilters}
        setFilters={(f) => {
          setUniFilters(f);
        }}
        onApply={() => setDrawerOpen(false)}
        clearFilters={clearFilters}
        countries={props.countries}
        resetSearchResult={props.resetSearchResult}
      />
    </Paper>
  );
};

export default UniversitySearchSection;
