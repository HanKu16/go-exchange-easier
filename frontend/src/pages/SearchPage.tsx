import { useState } from "react";
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
} from "@mui/material";
import Navbar from "../components/Navbar";
import FilterListIcon from "@mui/icons-material/Tune";
import CloseIcon from "@mui/icons-material/Close";
import LocationOnOutlinedIcon from "@mui/icons-material/LocationOnOutlined";
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import SettingsSuggestIcon from "@mui/icons-material/SettingsSuggest";

const MOCK_CITIES_BY_COUNTRY: Record<string, string[]> = {
  Poland: ["Warsaw", "Krakow", "Wroclaw", "Gdansk", "Poznan"],
  Spain: ["Madrid", "Barcelona", "Valencia", "Seville"],
  Italy: ["Rome", "Milan", "Naples"],
  Germany: ["Berlin", "Munich", "Hamburg"],
  France: ["Paris", "Lyon", "Marseille"],
};

const MOCK_UNIVERSITIES_BY_CITY: Record<string, string[]> = {
  Warsaw: ["University of Warsaw", "Warsaw University of Technology"],
  Krakow: ["Jagiellonian University", "AGH UST"],
  Madrid: ["Complutense University of Madrid"],
  Barcelona: ["University of Barcelona"],
};

const MOCK_MAJORS = [
  "Computer Science",
  "Economics",
  "Medicine",
  "Law",
  "Engineering",
  "Arts",
];

type SearchEntity = "user" | "university";
type SearchMode = "simple" | "filters";

type UserFilterState = {
  country: string | null;
  city: string | null;
  universityName: string | null;
  major: string | null;
  minYear: string;
  maxYear: string;
};

type UniversityFilterState = {
  country: string | null;
  city: string | null;
};

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
        props.setSeachQuery("");
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

const UserFilterDrawer = (props: {
  open: boolean;
  onClose: () => void;
  filters: UserFilterState;
  setFilters: (f: UserFilterState) => void;
  onApply: () => void;
  onReset: () => void;
}) => {
  const handleCountryChange = (_: any, v: string | null) =>
    props.setFilters({
      ...props.filters,
      country: v,
      city: null,
      universityName: null,
    });
  const handleCityChange = (_: any, v: string | null) =>
    props.setFilters({ ...props.filters, city: v, universityName: null });

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
            <Autocomplete
              options={Object.keys(MOCK_CITIES_BY_COUNTRY)}
              value={props.filters.country}
              onChange={handleCountryChange}
              renderInput={(p) => (
                <TextField {...p} label="Country" variant="filled" />
              )}
            />
            <Autocomplete
              options={
                props.filters.country
                  ? MOCK_CITIES_BY_COUNTRY[props.filters.country]
                  : []
              }
              disabled={!props.filters.country}
              value={props.filters.city}
              onChange={handleCityChange}
              renderInput={(p) => (
                <TextField {...p} label="City" variant="filled" />
              )}
            />
            <Autocomplete
              options={
                props.filters.city
                  ? MOCK_UNIVERSITIES_BY_CITY[props.filters.city] || []
                  : []
              }
              disabled={!props.filters.city}
              value={props.filters.universityName}
              onChange={(_, v) =>
                props.setFilters({ ...props.filters, universityName: v })
              }
              renderInput={(p) => (
                <TextField {...p} label="University" variant="filled" />
              )}
            />
          </Stack>
        </Box>
        <Box>
          <Typography variant="subtitle2" fontWeight="bold" mb={2}>
            STUDIES
          </Typography>
          <Autocomplete
            options={MOCK_MAJORS}
            value={props.filters.major}
            onChange={(_, v) =>
              props.setFilters({ ...props.filters, major: v })
            }
            renderInput={(p) => (
              <TextField {...p} label="Major" variant="outlined" />
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
          onClick={props.onApply}
          sx={{ py: 1.5, borderRadius: 3, bgcolor: "black" }}
        >
          Apply
        </Button>
        <Button onClick={props.onReset} sx={{ color: "text.secondary" }}>
          Reset
        </Button>
      </Stack>
    </Drawer>
  );
};

const UniversityFilterDrawer = (props: {
  open: boolean;
  onClose: () => void;
  filters: UniversityFilterState;
  setFilters: (f: UniversityFilterState) => void;
  onApply: () => void;
  onReset: () => void;
}) => {
  const handleCountryChange = (_: any, newValue: string | null) => {
    props.setFilters({ ...props.filters, country: newValue, city: null });
  };

  const handleCityChange = (_: any, newValue: string | null) => {
    props.setFilters({ ...props.filters, city: newValue });
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
            <Autocomplete
              options={Object.keys(MOCK_CITIES_BY_COUNTRY)}
              value={props.filters.country}
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
              options={
                props.filters.country
                  ? MOCK_CITIES_BY_COUNTRY[props.filters.country]
                  : []
              }
              disabled={!props.filters.country}
              value={props.filters.city}
              onChange={handleCityChange}
              noOptionsText="No cities found"
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="City"
                  placeholder={
                    props.filters.country
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
          onClick={props.onApply}
          sx={{
            py: 1.5,
            borderRadius: 3,
            fontWeight: "bold",
            bgcolor: "black",
          }}
        >
          Apply Filters
        </Button>
        <Button
          variant="text"
          onClick={props.onReset}
          sx={{ color: "text.secondary" }}
        >
          Clear all
        </Button>
      </Stack>
    </Drawer>
  );
};

const SearchButton = () => {
  return (
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
    >
      Search
    </Button>
  );
};

const UserSearchSection = () => {
  const [searchNick, setSearchNick] = useState<string>("");
  const [mode, setMode] = useState<SearchMode>("simple");
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [userFilters, setUserFilters] = useState<UserFilterState>({
    country: null,
    city: null,
    universityName: null,
    major: null,
    minYear: "",
    maxYear: "",
  });

  const handleModeChange = (e: SelectChangeEvent) =>
    setMode(e.target.value as SearchMode);

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
      <SearchButton />
      <UserFilterDrawer
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
        filters={userFilters}
        setFilters={setUserFilters}
        onApply={() => setDrawerOpen(false)}
        onReset={() =>
          setUserFilters({
            country: null,
            city: null,
            universityName: null,
            major: null,
            minYear: "",
            maxYear: "",
          })
        }
      />
    </Paper>
  );
};

const UniversitySearchSection = () => {
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [uniNameType, setUniNameType] = useState<"english" | "native">(
    "english"
  );
  const [searchName, setSearchName] = useState("");

  const [uniFilters, setUniFilters] = useState<UniversityFilterState>({
    country: null,
    city: null,
  });

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
              ? "Enter English name..."
              : "Enter Native name..."
          }
          value={searchName}
          onChange={(e) => setSearchName(e.target.value)}
        />
      </Box>

      <IconButton
        onClick={() => setDrawerOpen(true)}
        sx={{ color: "primary.main", mr: 1 }}
      >
        <FilterListIcon />
      </IconButton>

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
      >
        Search
      </Button>
      <UniversityFilterDrawer
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
        filters={uniFilters}
        setFilters={setUniFilters}
        onApply={() => setDrawerOpen(false)}
        onReset={() => setUniFilters({ country: null, city: null })}
      />
    </Paper>
  );
};

const SearchPage = () => {
  const [currentSearchEntity, setCurrentSearchEntity] =
    useState<SearchEntity>("user");
  const [searchQuery, setSearchQuery] = useState("");

  return (
    <Box sx={{ minHeight: "100vh", backgroundColor: "#ffffff" }}>
      <Navbar />
      <Container
        maxWidth="md"
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
          setCurrentSearchEntity={setCurrentSearchEntity}
          setSeachQuery={setSearchQuery}
        />
        {currentSearchEntity === "user" ? (
          <UserSearchSection />
        ) : (
          <UniversitySearchSection />
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
