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
  const [selectedCountryId, setSelectedCountryId] = useState<
    number | null | undefined
  >(undefined);
  const [selectedCityId, setSelectedCityId] = useState<number | null>(null);
  const [selectedUniversityId, setSelectedUniversityId] = useState<
    number | null
  >(null);
  const [selectedMajorId, setSelectedMajorId] = useState<number | null>(null);
  const [selectedMinYear, setSelectedMinYear] = useState<number | null>(null);
  const [selectedMaxYear, setSelectedMaxYear] = useState<number | null>(null);
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

  const getCities = async () => {
    const result = await sendGetCitiesRequest(selectedCountryId);
    if (result.isSuccess) {
      setCities(result.data.content);
    } else {
      setCities([]);
      showAlert("Failed to load cities.", "error");
    }
  };

  const getUniversities = async () => {
    const result = await sendGetUniversitiesRequest(
      null,
      null,
      selectedCityId,
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

  useEffect(() => {
    if (selectedCountryId === undefined) {
      return;
    }
    setSelectedCityId(null);
    setSelectedUniversityId(null);
    setCities([]);
    setUniversities([]);
    props.resetSearchResult();
    if (selectedCountryId !== null) {
      getCities();
    }
    props.setFilters({
      ...props.filters,
      countryId: selectedCountryId != undefined ? selectedCountryId : null,
      cityId: null,
      universityId: null,
    });
  }, [selectedCountryId]);

  useEffect(() => {
    if (selectedCountryId === null || selectedCountryId === undefined) {
      return;
    }
    setSelectedUniversityId(null);
    setUniversities([]);
    if (selectedCityId !== null) {
      getUniversities();
    }
    props.resetSearchResult();
    props.setFilters({
      ...props.filters,
      cityId: selectedCityId,
      universityId: null,
    });
  }, [selectedCityId]);

  useEffect(() => {
    if (
      selectedCountryId === null ||
      selectedCountryId === undefined ||
      selectedCityId === null
    ) {
      return;
    }
    props.setFilters({
      ...props.filters,
      universityId: selectedUniversityId,
    });
    props.resetSearchResult();
  }, [selectedUniversityId]);

  useEffect(() => {
    props.resetSearchResult();
    props.setFilters({
      ...props.filters,
      majorId: selectedMajorId,
    });
  }, [selectedMajorId]);

  useEffect(() => {
    props.resetSearchResult();
    props.setFilters({
      ...props.filters,
      minYear: selectedMinYear,
    });
  }, [selectedMinYear]);

  useEffect(() => {
    props.resetSearchResult();
    props.setFilters({
      ...props.filters,
      maxYear: selectedMaxYear,
    });
  }, [selectedMaxYear]);

  const resetButtonHandler = () => {
    props.resetSearchResult();
    setSelectedCountryId(null);
    setSelectedCityId(null);
    setSelectedUniversityId(null);
    setSelectedMajorId(null);
    setSelectedMinYear(null);
    setSelectedMaxYear(null);
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
                  setSelectedMajorId(newId);
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
              value={selectedMinYear ?? ""}
              onChange={(e) =>
                setSelectedMinYear(
                  e.target.value ? Number(e.target.value) : null,
                )
              }
              sx={{ width: "50%" }}
            />
            <TextField
              label="To"
              type="number"
              value={selectedMaxYear ?? ""}
              onChange={(e) =>
                setSelectedMaxYear(
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
