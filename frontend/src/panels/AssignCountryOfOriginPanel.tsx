import { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import type { ReactElement } from "react";
import { Radio, RadioGroup } from "@mui/material";
import { FormControl, Button } from "@mui/material";
import { sendAssignCountryOfOriginRequest } from "../utils/api/user";
import { FormControlLabel } from "@mui/material";
import type { Country } from "../types/Country";
import PanelHeader from "../components/PanelHeader";
import { useSnackbar } from "../context/SnackBarContext";
import type { DataFetchStatus } from "../types/DataFetchStatus";
import LoadingContent from "../components/LoadingContent";
import ContentLoadError from "../components/ContentLoadError";
import { sendGetCountriesRequest } from "../utils/api/country";

const AssignCountryOfOriginPanel = () => {
  const [countriesFetchStatus, setCountriesFetchStatus] =
    useState<DataFetchStatus>("loading");
  const [selectedCountryId, setSelectedCountryId] = useState<number | null>(
    null,
  );
  const [showConfirmButton, setShowConfirmButton] = useState<boolean>(false);
  const [countries, setCountries] = useState<Country[]>([]);
  const { showAlert } = useSnackbar();

  const getCountries = async () => {
    const result = await sendGetCountriesRequest();
    if (result.isSuccess) {
      const allCountries: Country[] = result.data.content.map((c) => ({
        id: c.id,
        name: c.englishName,
        flagUrl: c.flagUrl,
      }));
      setCountries(allCountries);
      setCountriesFetchStatus("success");
    } else {
      switch (result.error.status) {
        case "INTERNAL_SERVER_ERROR":
          setCountriesFetchStatus("serverError");
          break;
        case "SERVICE_UNAVAILABLE":
          setCountriesFetchStatus("connectionError");
          break;
      }
    }
  };

  const handleCountryAssignment = async () => {
    setShowConfirmButton(false);
    showAlert("Waiting for server response.", "info");
    const result = await sendAssignCountryOfOriginRequest({
      countryId: selectedCountryId,
    });
    if (result.isSuccess) {
      showAlert("Country was assigned to user successfully.", "success");
    } else {
      showAlert("Failed to assign country. Please try again later.", "error");
      setShowConfirmButton(true);
    }
  };

  const handleUnassignCountry = async () => {
    showAlert("Waiting for server response.", "info");
    const result = await sendAssignCountryOfOriginRequest({
      countryId: null,
    });
    if (result.isSuccess) {
      showAlert("Country assignment was successfully cleared.", "success");
      setSelectedCountryId(null);
      setShowConfirmButton(false);
    } else {
      showAlert("Clearing country assignment failed. Please try again later.", "error");
    }
  };

  const getInteractionElement = (countryId: number): ReactElement => {
    if (showConfirmButton && selectedCountryId === countryId) {
      return (
        <Button
          variant="contained"
          size="small"
          sx={{ marginLeft: 2, backgroundColor: "#182c44", "&:hover": { backgroundColor: "#244164" } }}
          onClick={handleCountryAssignment}
        >
          CONFIRM
        </Button>
      );
    }
    return <></>;
  };

  useEffect(() => {
    getCountries();
  }, []);

  useEffect(() => {
    setShowConfirmButton(true);
  }, [selectedCountryId]);

  const getContent = () => {
    switch (countriesFetchStatus) {
      case "success":
        return (
          <>
            <Box
              sx={{
                display: "flex",
                justifyContent: "flex-start", 
                ml: 2,
                mr: 2,
                mt: 2,
                mb: 1,
              }}
            >
              <Button
                variant="outlined"
                color="error" 
                onClick={handleUnassignCountry}
                sx={{
                  borderRadius: "12px",
                  textTransform: "none",
                  width: { xs: "100%", sm: "auto" } 
                }}
              >
                Clear assigned country
              </Button>
            </Box>
            <FormControl sx={{ ml: 2, mt: 0 }}>
              <RadioGroup
                name="countries-buttons-group"
                value={selectedCountryId}
                onChange={(event) => {
                  setSelectedCountryId(Number(event.target.value));
                }}
              >
                {countries.map((c) => (
                  <FormControlLabel
                    key={c.id}
                    value={c.id}
                    control={<Radio sx={{ '&.Mui-checked': { color: '#182c44' } }} />}
                    label={
                      <>
                        {c.name}
                        {c.flagUrl ? (
                          <img
                            src={c.flagUrl}
                            style={{ height: "0.8rem", marginLeft: 3 }}
                          />
                        ) : null}
                        {getInteractionElement(c.id)}
                      </>
                    }
                  />
                ))}
              </RadioGroup>
            </FormControl>
          </>
        );
      case "loading":
        return <LoadingContent title="Loading countries" />;
      case "connectionError":
        return (
          <ContentLoadError
            title="Connection error"
            subheader="Failed to load countries"
          />
        );
      case "serverError":
        return (
          <ContentLoadError
            title="Server error"
            subheader="Failed to load countries"
          />
        );
    }
  };

  return (
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <PanelHeader label="Assign country of origin to yourself" />
      {getContent()}
    </Box>
  );
};

export default AssignCountryOfOriginPanel;
