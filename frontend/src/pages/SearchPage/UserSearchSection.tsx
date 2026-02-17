import { useEffect, useState } from "react";
import {
  Box,
  FormControl,
  IconButton,
  InputBase,
  MenuItem,
  Select,
  Typography,
  Paper,
  Divider,
  type SelectChangeEvent,
} from "@mui/material";
import FilterListIcon from "@mui/icons-material/Tune";
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import SettingsSuggestIcon from "@mui/icons-material/SettingsSuggest";
import SearchIcon from "@mui/icons-material/Search";
import SearchResultTable from "../../components/SearchResult";
import { sendGetUsersRequest } from "../../utils/user";
import { type UserDetails } from "../../dtos/user/UserDetails";
import { sendGetExchangesRequest } from "../../utils/api/exchange";
import type { ExchangeDetails } from "../../dtos/exchange/ExchangeDetails";
import type {
  SearchMode,
  SearchResultFetchStatus,
  SearchSectionProps,
  UserFilterState,
} from "./types";
import SearchButton from "./SearchButton";
import UserFilterDrawer from "./UserFilterDrawer";

const UserSearchSection = (props: SearchSectionProps) => {
  const [searchNick, setSearchNick] = useState<string>("");
  const [mode, setMode] = useState<SearchMode>("simple");
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [userFilters, setUserFilters] = useState<UserFilterState>({
    countryId: null,
    cityId: null,
    universityId: null,
    majorId: null,
    minYear: null,
    maxYear: null,
  });
  const [users, setUsers] = useState<UserDetails[] | null>(null);
  const [exchanges, setExchanges] = useState<ExchangeDetails[]>([]);
  const [totalPagesCount, setTotalPagesCount] = useState<number | undefined>(
    undefined,
  );
  const [fetchStatus, setFetchStatus] = useState<SearchResultFetchStatus>(
    "fetchingWasNotStarted",
  );

  const handleModeChange = (e: SelectChangeEvent) => {
    props.resetSearchResult();
    setMode(e.target.value as SearchMode);
    if (e.target.value === "filters") {
      setSearchNick("");
    } else if (e.target.value === "simple") {
      setUserFilters({
        countryId: null,
        cityId: null,
        universityId: null,
        majorId: null,
        minYear: null,
        maxYear: null,
      });
    }
  };

  const getUsers = async () => {
    if (props.currentPage === undefined) {
      return;
    }
    setFetchStatus("loading");
    if (mode === "simple") {
      const result = await sendGetUsersRequest(
        searchNick,
        props.currentPage,
        props.pageSize,
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
        userFilters.minYear === null ? null : `${userFilters.minYear}-01-01`,
        userFilters.maxYear === null ? null : `${userFilters.maxYear}-12-31`,
        null,
      );
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
    if (fetchStatus === "fetchingWasNotStarted") {
      return;
    }
    if (fetchStatus === "loading") {
      props.setSearchResult({
        status: "loading",
        resultComponent: undefined,
        totalNumberOfPages: undefined,
      });
      return;
    }
    if (fetchStatus === "success") {
      if (mode === "simple") {
        props.setSearchResult({
          status: "success",
          resultComponent: getSuccessUsersSearchResult(),
          totalNumberOfPages: totalPagesCount,
        });
      } else if (mode === "filters") {
        props.setSearchResult({
          status: "success",
          resultComponent: getSuccessExchangesSearchResult(),
          totalNumberOfPages: totalPagesCount,
        });
      }
    } else {
      props.setSearchResult({
        status: fetchStatus,
        resultComponent: undefined,
        totalNumberOfPages: undefined,
      });
    }
  }, [fetchStatus]);

  useEffect(() => {
    if (props.currentPage === undefined) {
      return;
    }
    getUsers();
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
              toShow: <span>{e.user.nick}</span>,
              route: `/users/${e.user.id}`,
            },
            {
              toShow: (
                <span style={{ display: "inline-flex", alignItems: "center" }}>
                  {" "}
                  {e.university.englishName || e.university.nativeName}{" "}
                  <img
                    src={e.university.city.country.flagUrl}
                    style={{ height: "0.8rem", marginLeft: "6px" }}
                    onError={(event) =>
                      (event.currentTarget.style.display = "none")
                    }
                  />{" "}
                </span>
              ),
              route: `/universities/${e.university.id}`,
            },
            {
              toShow: e.fieldOfStudy.name,
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
              toShow: <span>{u.nick}</span>,
              route: `/users/${u.id}`,
            },
            {
              toShow: u.university ? (
                <span style={{ display: "inline-flex", alignItems: "center" }}>
                  {u.university.englishName}
                  <img
                    src={u.university.city.country.flagUrl}
                    style={{ height: "0.8rem", marginLeft: "6px" }}
                    onError={(event) =>
                      (event.currentTarget.style.display = "none")
                    }
                  />
                </span>
              ) : (
                "unknown"
              ),
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
                      src={u.country.flagUrl}
                      style={{ height: "0.8rem", marginLeft: "6px" }}
                      onError={(event) =>
                        (event.currentTarget.style.display = "none")
                      }
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
      <FormControl
        variant="standard"
        sx={{
          minWidth: { xs: 110, md: 140 },
          mr: { xs: 1, md: 2 },
        }}
      >
        <Select
          value={mode}
          onChange={handleModeChange}
          disableUnderline
          sx={{
            fontWeight: 700,
            fontSize: { xs: "0.8rem", md: "0.9rem" },
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
          {props.countries.length > 0 && (
            <MenuItem value="filters">
              <SettingsSuggestIcon fontSize="small" /> By Exchange
            </MenuItem>
          )}
        </Select>
      </FormControl>
      <Divider
        orientation="vertical"
        sx={{ height: 28, mr: { xs: 1, md: 2 } }}
      />
      {mode === "simple" ? (
        <Box sx={{ flex: 1, display: "flex", alignItems: "center" }}>
          <InputBase
            sx={{
              flex: 1,
              fontWeight: 500,
              fontSize: { xs: "0.95rem", md: "1.1rem" },
            }}
            placeholder="Enter nickname"
            value={searchNick}
            onChange={(e) => {
              setSearchNick(e.target.value);
              if (fetchStatus === "success") {
                props.resetSearchResult();
              }
              if (props.currentPage !== undefined) {
                props.setCurrentPage(undefined);
              }
            }}
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
          <Typography
            color="text.secondary"
            sx={{ fontSize: { xs: "0.9rem", md: "1.1rem" } }}
          >
            Set filters
          </Typography>
        </Box>
      )}
      {mode === "filters" && (
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
        onClick={getUsers}
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
      <Box sx={{ display: { xs: "none", sm: "block" }, ml: 1 }}>
        <SearchButton onClick={() => props.setCurrentPage(0)} />
      </Box>
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

export default UserSearchSection;
