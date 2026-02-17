import { useEffect, useState } from "react";
import {
  Autocomplete,
  Box,
  Button,
  Drawer,
  IconButton,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import type { Country } from "../../types/Country";
import { sendGetFieldsOfStudiesRequest } from "../../utils/api/field-of-study";
import type { City } from "../../types/City";
import { sendGetCitiesRequest } from "../../utils/api/city";
import { sendGetUniversitiesRequest } from "../../utils/api/university";
import type { UniversityDetails } from "../../dtos/university/UniversityDetails";
import { useSnackbar } from "../../context/SnackBarContext";
import type { Major, UserFilterDrawerProps } from "./types";

const UserFilterDrawer = (props: UserFilterDrawerProps) => {
  const [majors, setMajors] = useState<Major[]>([]);
  const [showMajorsDropdown, setShowMajorsDropdown] = useState(false);
  const [cities, setCities] = useState<City[]>([]);
  const [universities, setUniversities] = useState<UniversityDetails[]>([]);
  const { showAlert } = useSnackbar();

  const getMajors = async () => {
    const result = await sendGetFieldsOfStudiesRequest();
    if (result.isSuccess) {
      setMajors(result.data.content);
      setShowMajorsDropdown(true);
    } else {
      setShowMajorsDropdown(false);
    }
  };

  const getCities = async (countryId: number) => {
    const result = await sendGetCitiesRequest(countryId);
    if (result.isSuccess) {
      setCities(result.data.content);
    } else {
      setCities([]);
      showAlert("Failed to load cities.", "error");
    }
  };

  const getUniversities = async (cityId: number) => {
    const result = await sendGetUniversitiesRequest(
      null,
      null,
      cityId,
      null,
      0,
      100,
      "englishName,asc",
    );
    if (result.isSuccess) {
      setUniversities(result.data.content);
    } else {
      setUniversities([]);
      showAlert("Failed to load universities.", "error");
    }
  };

  useEffect(() => {
    getMajors();
  }, []);

  const handleCountryChange = (newId: number | null) => {
    props.setFilters({
      ...props.filters,
      countryId: newId,
      cityId: null,
      universityId: null,
    });
    if (newId === null) {
      return;
    }
    getCities(newId);
  };

  const handleCityChange = (newId: number | null) => {
    props.setFilters({
      ...props.filters,
      cityId: newId,
      universityId: null,
    });
    if (newId === null) {
      return;
    }
    getUniversities(newId);
  };

  const handleUniversityChange = (newId: number | null) => {
    props.setFilters({
      ...props.filters,
      universityId: newId,
    });
  };

  const handleMajorChange = (newId: number | null) => {
    props.setFilters({
      ...props.filters,
      majorId: newId,
    });
  };

  const handleMinYearChange = (newMinYear: number | null) => {
    props.setFilters({
      ...props.filters,
      minYear: newMinYear,
    });
  };

  const handleMaxYearChange = (newMaxYear: number | null) => {
    props.setFilters({
      ...props.filters,
      maxYear: newMaxYear,
    });
  };

  useEffect(() => {
    console.log("filters changed", props.filters);
    props.resetSearchResult();
  }, [props.filters]);

  const resetButtonHandler = () => {
    props.resetSearchResult();
    props.setFilters({
      countryId: null,
      cityId: null,
      universityId: null,
      majorId: null,
      minYear: null,
      maxYear: null,
    });
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
          Filter by exchange details
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
                props.countries.find((c) => c.id === props.filters.countryId) ||
                null
              }
              getOptionLabel={(c) => c.name}
              onChange={(_, newValue) => {
                const newId = newValue ? newValue.id : null;
                handleCountryChange(newId);
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
              value={cities.find((c) => c.id === props.filters.cityId) || null}
              getOptionLabel={(c) => c.name}
              disabled={!props.filters.countryId}
              onChange={(_, newValue) => {
                const newId = newValue ? newValue.id : null;
                handleCityChange(newId);
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
                universities.find((c) => c.id === props.filters.universityId) ||
                null
              }
              getOptionLabel={(c) => c.englishName || c.nativeName}
              disabled={!props.filters.cityId || !props.filters.countryId}
              onChange={(_, newValue) => {
                const newId = newValue ? newValue.id : null;
                handleUniversityChange(newId);
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
          {showMajorsDropdown && (
            <>
              <Typography variant="subtitle2" fontWeight="bold" mb={2}>
                STUDIES
              </Typography>
              <Autocomplete<Major>
                options={majors}
                getOptionLabel={(option) => option.name}
                value={
                  majors.find((m) => m.id === props.filters.majorId) || null
                }
                onChange={(_, newValue) => {
                  const newId = newValue ? newValue.id : null;
                  handleMajorChange(newId);
                }}
                renderInput={(params) => (
                  <TextField {...params} label="Major" variant="filled" />
                )}
              />
            </>
          )}
        </Box>
        <Box>
          <Typography variant="subtitle2" fontWeight="bold" mb={2}>
            YEARS RANGE
          </Typography>
          <Stack direction="row" spacing={2}>
            <TextField
              label="From"
              type="number"
              value={props.filters.minYear ?? ""}
              onChange={(e) =>
                handleMinYearChange(
                  e.target.value ? Number(e.target.value) : null,
                )
              }
              sx={{ width: "50%" }}
            />
            <TextField
              label="To"
              type="number"
              value={props.filters.maxYear ?? ""}
              onChange={(e) =>
                handleMaxYearChange(
                  e.target.value ? Number(e.target.value) : null,
                )
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

export default UserFilterDrawer;
