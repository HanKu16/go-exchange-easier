import { useEffect, useRef, useState, type ReactNode } from "react";
import {
  Box,
  Button,
  Container,
  Drawer,
  FormControl,
  IconButton,
  InputBase,
  MenuItem,
  Select,
  Stack,
  Tab,
  Tabs,
  TextField,
  Typography,
  Paper,
  Divider,
  Autocomplete,
  type SelectChangeEvent,
  Pagination,
} from "@mui/material";
import Navbar from "../components/Navbar";
import FilterListIcon from "@mui/icons-material/Tune";
import CloseIcon from "@mui/icons-material/Close";
import LocationOnOutlinedIcon from "@mui/icons-material/LocationOnOutlined";
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import SettingsSuggestIcon from "@mui/icons-material/SettingsSuggest";
import type { Country } from "../types/Country";
import { sendGetCountriesRequest } from "../utils/country";
import { sendGetUniversityMajorsRequest } from "../utils/university-major";
import type { UniversityMajorSummary } from "../dtos/summary/UniversityMajorSummary";
import type { City } from "../types/City";
import { sendGetCitiesRequest } from "../utils/city";
import { sendGetUniversitiesRequest } from "../utils/university";
import type { UniversityDetails } from "../dtos/details/UniversityDetails";
import SearchResultTable from "../components/SearchResult";
import type { DataFetchStatus } from "../types/DataFetchStatus";
import LoadingContent from "../components/LoadingContent";
import ContentLoadError from "../components/ContentLoadError";
import ContentEmpty from "../components/ContentEmpty";
import { sendGetUsersRequest } from "../utils/user";
import { type UserDetails } from "../dtos/details/UserDetails";
import { sendGetExchangesRequest } from "../utils/exchange";
import type { ExchangeDetails } from "../dtos/details/ExchangeDetails";

type SearchEntity = "user" | "university";
type SearchMode = "simple" | "filters";

type UserFilterState = {
  countryId: number | null;
  cityId: number | null;
  universityId: number | null;
  majorId: number | null;
  minYear: string;
  maxYear: string;
};

type UniversityFilterState = {
  englishName: string | null;
  nativeName: string | null;
  countryId: number | null;
  cityId: number | null;
};

type Major = UniversityMajorSummary;

const PageHeaders = () => (
  <>
    <Typography
      variant="h3"
      component="h1"
      fontWeight="800"
      sx={{ mb: 1, textAlign: "center" }}
    >
      Explore our community
    </Typography>
    <Typography
      variant="body1"
      color="text.secondary"
      sx={{ mb: 5, textAlign: "center", maxWidth: 500 }}
    >
      Find people and universities that match your exchange preferences
    </Typography>
  </>
);

const SearchEntitiesOptions = (props: any) => (
  <Paper
    elevation={0}
    sx={{
      p: "4px",
      mb: 3,
      bgcolor: "#f3f4f6",
      borderRadius: "12px",
      display: "inline-flex",
    }}
  >
    <Tabs
      value={props.currentSearchEntity}
      onChange={(_, val) => {
        props.setCurrentSearchEntity(val);
      }}
      TabIndicatorProps={{ style: { display: "none" } }}
    >
      <Tab label="People" value="user" disableRipple sx={tabStyles} />
      <Tab
        label="Universities"
        value="university"
        disableRipple
        sx={tabStyles}
      />
    </Tabs>
  </Paper>
);

type UserFilterDrawerProps = {
  open: boolean;
  onClose: () => void;
  filters: UserFilterState;
  setFilters: (f: UserFilterState) => void;
  countries: Country[];
  resetSearchResult: () => void;
};

const UserFilterDrawer = (props: UserFilterDrawerProps) => {
  const [selectedCountryId, setSelectedCountryId] = useState<
    number | null | undefined
  >(undefined);
  const [selectedCityId, setSelectedCityId] = useState<number | null>(null);
  const [selectedUniversityId, setSelectedUniversityId] = useState<
    number | null
  >(null);
  const [selectedMajorId, setSelectedMajorId] = useState<number | null>(null);
  // const [selectedStartYear, setSelectedStartYear] = useState<number | null>(null);
  // const [selectedYear, setSelectedEndYear] = useState<number | null>(null);

  const [majors, setMajors] = useState<Major[]>([]);
  const [cities, setCities] = useState<City[]>([]);
  const [universities, setUniversities] = useState<UniversityDetails[]>([]);

  const getMajors = async () => {
    const result = await sendGetUniversityMajorsRequest();
    if (result.isSuccess) {
      setMajors(result.data.content);
    }
  };

  const getCities = async () => {
    const result = await sendGetCitiesRequest(selectedCountryId);
    if (result.isSuccess) {
      setCities(result.data.content);
    }
  };

  const getUniverisites = async () => {
    const result = await sendGetUniversitiesRequest(
      null,
      null,
      selectedCityId,
      null,
      0,
      100,
      "englishName,asc"
    );
    if (result.isSuccess) {
      setUniversities(result.data.content);
    }
  };

  useEffect(() => {
    getMajors();
  }, []);

  useEffect(() => {
    if (selectedCountryId === undefined) {
      return;
    }
    if (selectedCityId == null) {
      props.resetSearchResult();
    } else {
      setSelectedCityId(null);
      setCities([]);
    }
    getCities();
    props.setFilters({
      ...props.filters,
      countryId: selectedCountryId != undefined ? selectedCountryId : null,
      cityId: null,
    });
  }, [selectedCountryId]);

  useEffect(() => {
    if (selectedUniversityId == null) {
      props.resetSearchResult();
    } else {
      setSelectedUniversityId(null);
      setUniversities([]);
    }
    getUniverisites();
    props.setFilters({
      ...props.filters,
      cityId: selectedCityId,
      universityId: null,
    });
  }, [selectedCityId]);

  useEffect(() => {
    props.resetSearchResult();
    props.setFilters({
      ...props.filters,
      universityId: selectedUniversityId,
    });
  }, [selectedUniversityId]);

  useEffect(() => {
    props.resetSearchResult();
    props.setFilters({
      ...props.filters,
      majorId: selectedMajorId,
    });
  }, [selectedMajorId]);

  const resetButtonHandler = () => {
    props.resetSearchResult();
    setSelectedCountryId(null);
    setSelectedCityId(null);
    setSelectedUniversityId(null);
    setSelectedMajorId(null);
  };

  return (
    <Drawer
      anchor="right"
      open={props.open}
      onClose={props.onClose}
      PaperProps={{ sx: { width: { xs: "100%", sm: 400 }, p: 4 } }}
    >
      <Box sx={{ display: "flex", justifyContent: "space-between", mb: 4 }}>
        <Typography variant="h5" fontWeight="800">
          Filter Users
        </Typography>
        <IconButton onClick={props.onClose} sx={{ bgcolor: "#f5f5f5" }}>
          <CloseIcon />
        </IconButton>
      </Box>
      <Stack spacing={4} sx={{ flexGrow: 1 }}>
        <Box>
          <Typography variant="subtitle2" fontWeight="bold" mb={2}>
            LOCATION
          </Typography>
          <Stack spacing={2}>
            <Autocomplete<Country>
              options={props.countries}
              value={
                props.countries.find((c) => c.id === selectedCountryId) || null
              }
              getOptionLabel={(c) => c.name}
              onChange={(_, newValue) => {
                const newId = newValue ? newValue.id : null;
                setSelectedCountryId(newId);
              }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="Country"
                  placeholder="Select country"
                  variant="filled"
                  sx={{ "& .MuiFilledInput-root": { borderRadius: 2 } }}
                />
              )}
            />
            <Autocomplete<City>
              options={cities}
              value={cities.find((c) => c.id === selectedCityId) || null}
              getOptionLabel={(c) => c.name}
              disabled={!selectedCountryId}
              onChange={(_, newValue) => {
                const newId = newValue ? newValue.id : null;
                setSelectedCityId(newId);
              }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="City"
                  placeholder="Select city"
                  variant="filled"
                  sx={{ "& .MuiFilledInput-root": { borderRadius: 2 } }}
                />
              )}
            />
            <Autocomplete<UniversityDetails>
              options={universities}
              value={
                universities.find((c) => c.id === selectedUniversityId) || null
              }
              getOptionLabel={(c) => c.englishName || c.nativeName}
              disabled={!selectedCityId || !selectedCountryId}
              onChange={(_, newValue) => {
                const newId = newValue ? newValue.id : null;
                setSelectedUniversityId(newId);
              }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="University"
                  placeholder="Select university"
                  variant="filled"
                  sx={{ "& .MuiFilledInput-root": { borderRadius: 2 } }}
                />
              )}
            />
          </Stack>
        </Box>
        <Box>
          <Typography variant="subtitle2" fontWeight="bold" mb={2}>
            STUDIES
          </Typography>
          <Autocomplete<Major>
            options={majors}
            getOptionLabel={(option) => option.name}
            value={majors.find((m) => m.id === props.filters.majorId)}
            onChange={(_, newValue) => {
              const newId = newValue ? newValue.id : null;
              setSelectedMajorId(newId);
            }}
            renderInput={(params) => (
              <TextField {...params} label="Major" variant="filled" />
            )}
          />
        </Box>
        <Box>
          <Typography variant="subtitle2" fontWeight="bold" mb={2}>
            YEARS RANGE
          </Typography>
          <Stack direction="row" spacing={2}>
            <TextField
              label="From"
              type="number"
              value={props.filters.minYear}
              onChange={(e) =>
                props.setFilters({ ...props.filters, minYear: e.target.value })
              }
              sx={{ width: "50%" }}
            />
            <TextField
              label="To"
              type="number"
              value={props.filters.maxYear}
              onChange={(e) =>
                props.setFilters({ ...props.filters, maxYear: e.target.value })
              }
              sx={{ width: "50%" }}
            />
          </Stack>
        </Box>
      </Stack>
      <Stack spacing={2} mt={4}>
        <Button
          variant="contained"
          onClick={resetButtonHandler}
          sx={{ py: 1.5, borderRadius: 3, bgcolor: "black" }}
        >
          Reset
        </Button>
      </Stack>
    </Drawer>
  );
};

type UniversityFilterDrawerProps = {
  open: boolean;
  onClose: () => void;
  filters: UniversityFilterState;
  setFilters: (f: UniversityFilterState) => void;
  onApply: () => void;
  onReset: () => void;
  countries: Country[];
  resetSearchResult: () => void;
};

const UniversityFilterDrawer = (props: UniversityFilterDrawerProps) => {
  const [selectedCountryId, setSelectedCountryId] = useState<number | null>(
    null
  );
  const [cities, setCities] = useState<City[]>([]);

  const handleCountryChange = (_: any, newValue: Country | null) => {
    props.resetSearchResult();
    props.setFilters({
      ...props.filters,
      countryId: newValue ? newValue.id : null,
      cityId: null,
    });
    setSelectedCountryId(newValue ? newValue.id : null);
  };

  const handleCityChange = (_: any, newValue: City | null) => {
    props.resetSearchResult();
    props.setFilters({
      ...props.filters,
      cityId: newValue ? newValue.id : null,
    });
  };

  const getCities = async () => {
    const result = await sendGetCitiesRequest(selectedCountryId);
    if (result.isSuccess) {
      setCities(result.data.content);
    }
  };

  useEffect(() => {
    getCities();
  }, [selectedCountryId]);

  return (
    <Drawer
      anchor="right"
      open={props.open}
      onClose={props.onClose}
      PaperProps={{ sx: { width: { xs: "100%", sm: 400 }, p: 4 } }}
    >
      <Box sx={{ display: "flex", justifyContent: "space-between", mb: 4 }}>
        <Typography variant="h5" fontWeight="800">
          Filter Universities
        </Typography>
        <IconButton onClick={props.onClose} sx={{ bgcolor: "#f5f5f5" }}>
          <CloseIcon />
        </IconButton>
      </Box>

      <Stack spacing={4} sx={{ flexGrow: 1 }}>
        <Box>
          <Stack direction="row" spacing={1} alignItems="center" mb={2}>
            <LocationOnOutlinedIcon color="action" fontSize="small" />
            <Typography variant="subtitle2" fontWeight="bold">
              LOCATION
            </Typography>
          </Stack>
          <Stack spacing={2}>
            <Autocomplete<Country>
              options={props.countries}
              value={
                props.countries.find((c) => c.id === props.filters.countryId) ||
                null
              }
              getOptionLabel={(c) => c.name}
              onChange={handleCountryChange}
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="Country"
                  placeholder="Select country"
                  variant="filled"
                  sx={{ "& .MuiFilledInput-root": { borderRadius: 2 } }}
                />
              )}
            />
            <Autocomplete
              options={cities}
              value={cities.find((c) => c.id === props.filters.cityId) || null}
              getOptionLabel={(c) => c.name}
              disabled={!props.filters.countryId}
              onChange={handleCityChange}
              noOptionsText="No cities found"
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="City"
                  placeholder={
                    props.countries.length > 0
                      ? "Filter by city"
                      : "Select country first"
                  }
                  variant="filled"
                  sx={{ "& .MuiFilledInput-root": { borderRadius: 2 } }}
                />
              )}
            />
          </Stack>
        </Box>
      </Stack>

      <Stack spacing={2} sx={{ mt: 4 }}>
        <Button
          variant="contained"
          onClick={props.onReset}
          sx={{
            py: 1.5,
            borderRadius: 3,
            fontWeight: "bold",
            bgcolor: "black",
          }}
        >
          Clear all
        </Button>
      </Stack>
    </Drawer>
  );
};

type SearchButtonProps = {
  onClick: () => void;
};

const SearchButton = (props: SearchButtonProps) => {
  return (
    <Button
      onClick={props.onClick}
      variant="contained"
      sx={{
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
  );
};

const UserSearchSection = (props: SearchSectionProps) => {
  const [searchNick, setSearchNick] = useState<string>("");
  const [mode, setMode] = useState<SearchMode>("simple");
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [userFilters, setUserFilters] = useState<UserFilterState>({
    countryId: null,
    cityId: null,
    universityId: null,
    majorId: null,
    minYear: "",
    maxYear: "",
  });
  const [users, setUsers] = useState<UserDetails[] | null>(null);
  const [exchanges, setExchanges] = useState<ExchangeDetails[]>([]);
  const [totalPagesCount, setTotalPagesCount] = useState<number | undefined>(
    undefined
  );
  const previousPage = useRef(props.currentPage);
  const [fetchStatus, setFetchStatus] = useState<SearchResultFetchStatus>(
    "fetchingWasNotStarted"
  );

  const handleModeChange = (e: SelectChangeEvent) => {
    props.resetSearchResult();
    setMode(e.target.value as SearchMode);
  };

  const getUsers = async () => {
    setFetchStatus("loading");
    if (mode === "simple") {
      const result = await sendGetUsersRequest(
        searchNick,
        props.currentPage,
        props.pageSize
      );
      if (result.isSuccess) {
        setFetchStatus("success");
        setTotalPagesCount(result.data.totalPages);
        setUsers(result.data.content);
      } else {
        setUsers([]);
        switch (result.error.status) {
          case "INTERNAL_SERVER_ERROR":
            setFetchStatus("serverError");
            break;
          case "SERVICE_UNAVAILABLE":
            setFetchStatus("connectionError");
            break;
        }
      }
    } else if (mode === "filters") {
      const result = await sendGetExchangesRequest(
        props.currentPage,
        props.pageSize,
        "endAt,desc",
        userFilters.countryId,
        userFilters.universityId,
        userFilters.cityId,
        userFilters.majorId,
        userFilters.minYear,
        userFilters.maxYear,
        null
      );
      console.log(result);
      if (result.isSuccess) {
        setFetchStatus("success");
        setTotalPagesCount(result.data.totalPages);
        setExchanges(result.data.content);
      } else {
        setExchanges([]);
        switch (result.error.status) {
          case "INTERNAL_SERVER_ERROR":
            setFetchStatus("serverError");
            break;
          case "SERVICE_UNAVAILABLE":
            setFetchStatus("connectionError");
            break;
        }
      }
    }
  };

  useEffect(() => {
    if (fetchStatus === "fetchingWasNotStarted" || fetchStatus === "loading") {
      return;
    }
    if (mode === "simple") {
      if (fetchStatus === "success") {
        props.setSearchResult({
          status: "success",
          resultComponent: getSuccessUsersSearchResult(),
          totalNumberOfPages: totalPagesCount,
        });
      } else {
        props.resetSearchResult();
      }
    } else if (mode === "filters") {
      if (fetchStatus === "success") {
        props.setSearchResult({
          status: "success",
          resultComponent: getSuccessExchangesSearchResult(),
          totalNumberOfPages: totalPagesCount,
        });
      } else {
        props.resetSearchResult();
      }
    }
  }, [fetchStatus]);

  useEffect(() => {
    if (previousPage.current === props.currentPage) {
      return;
    }
    getUsers();
    previousPage.current = props.currentPage;
  }, [props.currentPage]);

  const getSuccessExchangesSearchResult = () => {
    if (!exchanges || exchanges.length === 0) {
      return undefined;
    }
    return (
      <SearchResultTable
        columnNames={["Nick", "University", "Major", "Start Date", "End Date"]}
        rows={exchanges.map((e) => ({
          id: e.id,
          data: [
            {
              toShow: e.user.nick,
              route: `/users/${e.user.id}`,
            },
            {
              toShow: e.university.englishName || "unknown",
              route: `/universities/${e.university.id}`,
            },
            {
              toShow: e.major.name,
              route: undefined,
            },
            {
              toShow: `${e.timeRange.startedAt}`,
              route: undefined,
            },
            {
              toShow: `${e.timeRange.endAt}`,
              route: undefined,
            },
          ],
        }))}
      />
    );
  };

  const getSuccessUsersSearchResult = () => {
    if (!users || users.length === 0) {
      return undefined;
    }
    return (
      <SearchResultTable
        columnNames={["Nick", "Home university", "Country of origin"]}
        rows={users.map((u) => ({
          id: u.id,
          data: [
            {
              toShow: u.nick || "",
              route: `/users/${u.id}`,
            },
            {
              toShow: u.university?.englishName || "unknown",
              route: `/universities/${u.university?.id}`,
            },
            {
              toShow:
                u.country && u.country.englishName ? (
                  <span
                    style={{ display: "inline-flex", alignItems: "center" }}
                  >
                    {u.country.englishName}
                    <img
                      src={`/flags/${u.country.englishName}.png`}
                      alt={u.country.englishName}
                      style={{ height: "0.8rem", marginLeft: "6px" }}
                    />
                  </span>
                ) : (
                  "unknown"
                ),
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
        p: "8px 8px 8px 24px",
        display: "flex",
        alignItems: "center",
        width: "100%",
        maxWidth: 800,
        borderRadius: "50px",
        border: "1px solid rgba(0,0,0,0.05)",
        boxShadow: "0px 4px 20px rgba(0,0,0,0.08)",
      }}
    >
      <FormControl variant="standard" sx={{ minWidth: 140, mr: 2 }}>
        <Select
          value={mode}
          onChange={handleModeChange}
          disableUnderline
          sx={{
            fontWeight: 700,
            fontSize: "0.9rem",
            color: "text.secondary",
            "& .MuiSelect-select": {
              display: "flex",
              alignItems: "center",
              gap: 1,
            },
          }}
        >
          <MenuItem value="simple">
            <PersonOutlineIcon fontSize="small" />
            By Nick
          </MenuItem>
          <MenuItem value="filters">
            <SettingsSuggestIcon fontSize="small" /> By Filters
          </MenuItem>
        </Select>
      </FormControl>

      <Divider orientation="vertical" sx={{ height: 28, mr: 2 }} />
      {mode === "simple" ? (
        <Box sx={{ flex: 1, display: "flex", alignItems: "center" }}>
          <InputBase
            sx={{ flex: 1, fontSize: "1.1rem", fontWeight: 500 }}
            placeholder="Enter nickname"
            value={searchNick}
            onChange={(e) => setSearchNick(e.target.value)}
          />
        </Box>
      ) : (
        <Box
          sx={{
            flex: 1,
            display: "flex",
            alignItems: "center",
            cursor: "pointer",
            opacity: 0.7,
          }}
          onClick={() => setDrawerOpen(true)}
        >
          <Typography fontSize="1.1rem" color="text.secondary">
            Click to set filters
          </Typography>
        </Box>
      )}
      {mode === "filters" && (
        <IconButton
          onClick={() => setDrawerOpen(true)}
          sx={{ color: "primary.main", mr: 1 }}
        >
          <FilterListIcon />
        </IconButton>
      )}
      <SearchButton onClick={getUsers} />
      <UserFilterDrawer
        countries={props.countries}
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
        filters={userFilters}
        setFilters={setUserFilters}
        resetSearchResult={props.resetSearchResult}
      />
    </Paper>
  );
};

type SearchSectionProps = {
  countries: Country[];
  setSearchResult: (searchResult: SearchResult) => void;
  currentPage: number;
  setCurrentPage: (currentPage: number) => void;
  pageSize: number;
  resetSearchResult: () => void;
};

type SearchResultFetchStatus = DataFetchStatus | "fetchingWasNotStarted";

const UniversitySearchSection = (props: SearchSectionProps) => {
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [uniNameType, setUniNameType] = useState<"english" | "native">(
    "english"
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
    undefined
  );
  const previousPage = useRef(props.currentPage);

  const getUniverisites = async () => {
    setUniversitiesFetchStatus("loading");
    const result = await sendGetUniversitiesRequest(
      uniFilters.englishName,
      uniFilters.nativeName,
      uniFilters.cityId,
      uniFilters.countryId,
      props.currentPage,
      props.pageSize,
      "englishName,asc"
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
    if (previousPage.current === props.currentPage) {
      return;
    }
    previousPage.current = props.currentPage;
    getUniverisites();
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
              toShow: u.nativeName || "",
              route: `/universities/${u.id}`,
            },
            {
              toShow: u.englishName || "",
              route: `/universities/${u.id}`,
            },
            { toShow: u.city?.name || "", route: undefined },
            {
              toShow:
                (
                  <>
                    {u.city?.country?.englishName}
                    <img
                      src={`/flags/${u.city?.country?.englishName}.png`}
                      style={{ height: "0.8rem", marginLeft: 2 }}
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
    <>
      <Paper
        elevation={4}
        sx={{
          p: "8px 8px 8px 24px",
          display: "flex",
          alignItems: "center",
          width: "90%",
          maxWidth: 800,
          borderRadius: "50px",
          border: "1px solid rgba(0,0,0,0.05)",
          boxShadow: "0px 4px 20px rgba(0,0,0,0.08)",
        }}
      >
        <Box sx={{ flex: 1, display: "flex", alignItems: "center" }}>
          <>
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
                mr: 1.5,
                minWidth: 80,
                "& .MuiSelect-icon": { color: "primary.main" },
              }}
            >
              <MenuItem value="english">ENG</MenuItem>
              <MenuItem value="native">NAT</MenuItem>
            </Select>
            <Divider orientation="vertical" sx={{ height: 20, mr: 2 }} />
          </>
          <InputBase
            sx={{ flex: 1, fontSize: "1.1rem", fontWeight: 500 }}
            placeholder={
              uniNameType === "english"
                ? "Enter English name"
                : "Enter Native name"
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
            sx={{ color: "primary.main", mr: 1 }}
          >
            <FilterListIcon />
          </IconButton>
        )}

        <Button
          variant="contained"
          sx={{
            borderRadius: "50px",
            px: 4,
            py: 1.5,
            fontWeight: 700,
            bgcolor: "black",
            "&:hover": { bgcolor: "#333" },
            ml: 1,
          }}
          onClick={getUniverisites}
        >
          Search
        </Button>
        <UniversityFilterDrawer
          open={drawerOpen}
          onClose={() => setDrawerOpen(false)}
          filters={uniFilters}
          setFilters={(f) => {
            props.setCurrentPage(0);
            setUniFilters(f);
          }}
          onApply={() => setDrawerOpen(false)}
          onReset={() => {
            previousPage.current = props.currentPage;
            props.resetSearchResult();
            setUniFilters({
              countryId: null,
              cityId: null,
              englishName: null,
              nativeName: null,
            });
          }}
          countries={props.countries}
          resetSearchResult={props.resetSearchResult}
        />
      </Paper>
    </>
  );
};

type SearchResult = {
  status: DataFetchStatus | "fetchingWasNotStarted";
  resultComponent: ReactNode | undefined;
  totalNumberOfPages: number | undefined;
};

const SearchPage = () => {
  const [currentSearchEntity, setCurrentSearchEntity] =
    useState<SearchEntity>("user");
  const [countries, setCountries] = useState<Country[]>([]);
  const [searchResult, setSearchResult] = useState<SearchResult>({
    status: "fetchingWasNotStarted",
    resultComponent: undefined,
    totalNumberOfPages: undefined,
  });
  const [currentPage, setCurrentPage] = useState<number>(0);

  const handleEntityChange = (newEntity: SearchEntity) => {
    setCurrentSearchEntity(newEntity);
    setSearchResult({
      status: "fetchingWasNotStarted",
      resultComponent: undefined,
      totalNumberOfPages: undefined,
    });
    setCurrentPage(0);
  };

  const resetSearchResult = () => {
    if (resetSearchResult === undefined) {
      return;
    }
    setSearchResult({
      status: "fetchingWasNotStarted",
      resultComponent: undefined,
      totalNumberOfPages: undefined,
    });
    setCurrentPage(0);
  };

  const getCountries = async () => {
    const result = await sendGetCountriesRequest();
    if (result.isSuccess) {
      const allCountries: Country[] = result.data.content.map((c) => ({
        id: c.id,
        name: c.englishName,
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

  useEffect(() => {
    console.log("Current page changed: ", currentPage);
  }, [currentPage]);

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
          searchResult.totalNumberOfPages > 1 && (
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

const tabStyles = {
  textTransform: "none",
  fontWeight: 600,
  fontSize: "0.95rem",
  color: "text.secondary",
  borderRadius: "8px",
  minHeight: "40px",
  px: 3,
  "&.Mui-selected": {
    color: "text.primary",
    backgroundColor: "#fff",
    boxShadow: "0px 2px 4px rgba(0,0,0,0.05)",
  },
} as const;

export default SearchPage;
