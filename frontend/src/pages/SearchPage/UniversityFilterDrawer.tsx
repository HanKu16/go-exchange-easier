import { useEffect, useState } from "react";
import {
  Box,
  Button,
  Drawer,
  IconButton,
  TextField,
  Typography,
  Autocomplete,
  Stack,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import LocationOnOutlinedIcon from "@mui/icons-material/LocationOnOutlined";
import type { Country } from "../../types/Country";
import type { City } from "../../types/City";
import { sendGetCitiesRequest } from "../../utils/api/city";
import { useSnackbar } from "../../context/SnackBarContext";
import type { UniversityFilterDrawerProps } from "./types";

const UniversityFilterDrawer = (props: UniversityFilterDrawerProps) => {
  const [selectedCountryId, setSelectedCountryId] = useState<
    number | null | undefined
  >(undefined);
  const [cities, setCities] = useState<City[]>([]);
  const { showAlert } = useSnackbar();

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
    } else {
      setCities([]);
      showAlert("Failed to load cities.", "error");
    }
  };

  useEffect(() => {
    if (selectedCountryId === undefined) {
      return;
    }
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

export default UniversityFilterDrawer;
