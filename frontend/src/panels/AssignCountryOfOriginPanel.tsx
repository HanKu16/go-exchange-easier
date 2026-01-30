import { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import type { ReactElement } from "react";
import { Radio, RadioGroup } from "@mui/material";
import { FormControl, Button } from "@mui/material";
import { sendAssignCountryOfOriginRequest } from "../utils/user";
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
      }));
      allCountries.push({ id: 0, name: "no country" });
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
    const countryId = selectedCountryId !== 0 ? selectedCountryId : null;
    showAlert("Waiting for server response.", "info");
    const result = await sendAssignCountryOfOriginRequest({
      countryId: countryId,
    });
    if (result.isSuccess) {
      showAlert("Country was assigned to user successfully.", "success");
    } else {
      showAlert("Failed to assign country. Please try again later.", "error");
      setShowConfirmButton(true);
    }
  };

  const getInteractionElement = (countryId: number): ReactElement => {
    if (showConfirmButton && selectedCountryId === countryId) {
      return (
        <Button
          variant="contained"
          size="small"
          sx={{ marginLeft: 2 }}
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
          <FormControl>
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
                  control={<Radio />}
                  label={
                    <>
                      {c.name}
                      {c.id !== 0 && (
                        <img
                          src={`/flags/${c.name}.png`}
                          style={{ height: "0.8rem", marginLeft: 3 }}
                        />
                      )}
                      {getInteractionElement(c.id)}
                    </>
                  }
                />
              ))}
            </RadioGroup>
          </FormControl>
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
    <Box>
      <PanelHeader label="Assign country of origin to yourself" />
      {getContent()}
    </Box>
  );
};

export default AssignCountryOfOriginPanel;
