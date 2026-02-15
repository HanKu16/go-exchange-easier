import { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import type { ReactElement } from "react";
import { InputLabel, MenuItem, Radio, RadioGroup, Select } from "@mui/material";
import { FormControl, Button } from "@mui/material";
import { FormControlLabel } from "@mui/material";
import { sendGetFieldsOfStudiesRequest } from "../utils/api/field-of-study";
import type { FieldOfStudySummary } from "../dtos/field-of-study/FieldOfStudySummary";
import type { UniversityNameLanguage } from "../types/UniversityNameLanguage";
import type { University } from "../types/University";
import type { Country } from "../types/Country";
import PanelHeader from "../components/PanelHeader";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import type { Dayjs } from "dayjs";
import type { CreateExchangeRequest } from "../dtos/exchange/CreateExchangeRequest";
import { sendCreateExchangeRequest } from "../utils/api/exchange";
import { getGlobalErrorCodes } from "../utils/error";
import type { DataFetchStatus } from "../types/DataFetchStatus";
import LoadingContent from "../components/LoadingContent";
import ContentLoadError from "../components/ContentLoadError";
import { useSnackbar } from "../context/SnackBarContext";
import { sendGetUniversitiesRequest } from "../utils/api/university";
import { sendGetCountriesRequest } from "../utils/api/country";
import Exchanges, { type ExchangesProps } from "../components/Exchanges";

type Major = FieldOfStudySummary;

type AddExchangeStage =
  | "Choose university"
  | "Choose major"
  | "Choose time range"
  | "Exchange added successfully";

type ChooseUniversitySubpanelProps = {
  selectedUniversityId: number | undefined;
  setSelectedUniversityId: (universityId: number) => void;
  setCurrentStage: (stage: AddExchangeStage) => void;
};

type ChooseMajorSubpanelProps = {
  selectedMajorId: number | undefined;
  setSelectedMajorId: (majorId: number) => void;
  setCurrentStage: (stage: AddExchangeStage) => void;
};

type ChooseTimeRangeSubpanel = {
  selectedUniversityId: number | undefined;
  selectedMajorId: number | undefined;
  setCurrentStage: (stage: AddExchangeStage) => void;
  setExchangesProps: (props: ExchangesProps) => void;
};

type ExchangeAddedSuccessfullySubpanelProps = {
  exchangesProps: ExchangesProps | undefined;
  setCurrentStage: (stage: AddExchangeStage) => void;
};

const ChooseUniversitySubpanel = (props: ChooseUniversitySubpanelProps) => {
  const [selectedCountryId, setSelectedCountryId] = useState<number | null>(
    null,
  );
  const [selectedUniversityNameLanguage, setSelectedUniversityNameLanguage] =
    useState<UniversityNameLanguage>("english");
  const [countries, setCountries] = useState<Country[]>([]);
  const [universities, setUniversities] = useState<University[]>([]);
  const [countriesFetchStatus, setCountriesFetchStatus] =
    useState<DataFetchStatus>("loading");
  const [universitiesFetchStatus, setUniversitiesFetchStatus] = useState<
    DataFetchStatus | undefined
  >(undefined);

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

  const getUniversitiesFromCountry = async () => {
    if (selectedCountryId === null) {
      return;
    }
    setUniversities([]);
    setUniversitiesFetchStatus("loading");
    const result = await sendGetUniversitiesRequest(
      null,
      null,
      null,
      selectedCountryId,
      0,
      1000,
      "englishName,asc",
    );
    if (result.isSuccess) {
      setUniversities(result.data.content);
      setUniversitiesFetchStatus("success");
    } else {
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

  const getInteractionElement = (universityId: number): ReactElement => {
    if (props.selectedUniversityId === universityId) {
      return (
        <Button
          variant="contained"
          size="small"
          sx={{ marginLeft: 2 }}
          onClick={() => {
            props.setSelectedUniversityId(universityId);
            props.setCurrentStage("Choose major");
          }}
        >
          NEXT STEP
        </Button>
      );
    }
    return <></>;
  };

  const getUniversitiesContent = () => {
    switch (universitiesFetchStatus) {
      case undefined:
        return <></>;
      case "loading":
        return <LoadingContent title="Loading universities" />;
      case "connectionError":
        return (
          <ContentLoadError
            title="Connection error"
            subheader="Failed to load universities"
          />
        );
      case "serverError":
        return (
          <ContentLoadError
            title="Server error"
            subheader="Failed to load universities"
          />
        );
      case "success":
        return (
          <>
            <FormControl>
              <RadioGroup
                name="universities-buttons-group"
                value={props.selectedUniversityId}
                onChange={(event) => {
                  props.setSelectedUniversityId(Number(event.target.value));
                }}
              >
                {universities.map((u) => (
                  <FormControlLabel
                    key={u.id}
                    value={u.id}
                    control={<Radio />}
                    label={
                      <>
                        {selectedUniversityNameLanguage === "english" &&
                        u.englishName
                          ? u.englishName
                          : u.nativeName}
                        {` [${u.city.name}]`}
                        {getInteractionElement(u.id)}
                      </>
                    }
                  />
                ))}
              </RadioGroup>
            </FormControl>
          </>
        );
    }
  };

  const getContent = () => {
    switch (countriesFetchStatus) {
      case "success":
        return (
          <>
            <Box
              sx={{
                display: "flex",
                flexDirection: { xs: "column", md: "row" },
              }}
            >
              <FormControl
                sx={{ m: 2, width: { xs: "100%", sm: "60%", lg: "40%" } }}
              >
                <InputLabel>Country</InputLabel>
                <Select
                  id="select-country-id"
                  autoWidth
                  label="Country"
                  value={selectedCountryId}
                  onChange={(e) => setSelectedCountryId(Number(e.target.value))}
                >
                  {countries.map((c) =>
                    c.id !== 0 ? (
                      <MenuItem key={c.id} value={c.id}>
                        {c.name}
                        {c.flagUrl ? (
                          <img
                            src={c.flagUrl}
                            style={{ height: "0.8rem", marginLeft: 3 }}
                          />
                        ) : null}
                      </MenuItem>
                    ) : null,
                  )}
                </Select>
              </FormControl>
              <FormControl
                sx={{ m: 2, width: { xs: "50%", sm: "30%", lg: "20%" } }}
              >
                <InputLabel>University name language</InputLabel>
                <Select
                  id="select-university-name-language-id"
                  autoWidth
                  label="University name language"
                  value={selectedUniversityNameLanguage}
                  onChange={() =>
                    setSelectedUniversityNameLanguage((prevState) =>
                      prevState === "english" ? "native" : "english",
                    )
                  }
                >
                  <MenuItem key={"english"} value={"english"}>
                    english
                  </MenuItem>
                  <MenuItem key={"native"} value={"native"}>
                    native
                  </MenuItem>
                </Select>
              </FormControl>
            </Box>
            {getUniversitiesContent()}
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

  useEffect(() => {
    getCountries();
  }, []);

  useEffect(() => {
    getUniversitiesFromCountry();
  }, [selectedCountryId]);

  return (
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <PanelHeader label="Find university where you were on exchange" />
      {getContent()}
    </Box>
  );
};

const ChooseMajorSubpanel = (props: ChooseMajorSubpanelProps) => {
  const [majors, setMajors] = useState<Major[]>([]);
  const [majorsFetchStatus, setMajorsFetchStatus] =
    useState<DataFetchStatus>("loading");

  const getInteractionElement = (majorId: number): ReactElement => {
    if (props.selectedMajorId === majorId) {
      return (
        <Button
          variant="contained"
          size="small"
          sx={{ marginLeft: 2 }}
          onClick={() => {
            props.setSelectedMajorId(majorId);
            props.setCurrentStage("Choose time range");
          }}
        >
          NEXT STEP
        </Button>
      );
    }
    return <></>;
  };

  const getMajors = async () => {
    const result = await sendGetFieldsOfStudiesRequest();
    if (result.isSuccess) {
      const majors = result.data;
      setMajors(majors.content);
      setMajorsFetchStatus("success");
    } else {
      switch (result.error.status) {
        case "INTERNAL_SERVER_ERROR":
          setMajorsFetchStatus("serverError");
          break;
        case "SERVICE_UNAVAILABLE":
          setMajorsFetchStatus("connectionError");
          break;
      }
    }
  };

  const getContent = () => {
    switch (majorsFetchStatus) {
      case "success":
        return (
          <Box
            sx={{ display: "flex", flexDirection: { xs: "column", md: "row" } }}
          >
            <RadioGroup
              name="universities-buttons-group"
              value={props.selectedMajorId}
              onChange={(event) => {
                props.setSelectedMajorId(Number(event.target.value));
              }}
            >
              {majors.map((m) => (
                <FormControlLabel
                  key={m.id}
                  value={m.id}
                  control={<Radio />}
                  label={
                    <>
                      {m.name}
                      {getInteractionElement(m.id)}
                    </>
                  }
                />
              ))}
            </RadioGroup>
          </Box>
        );
      case "loading":
        return <LoadingContent title="Loading majors" />;
      case "connectionError":
        return (
          <ContentLoadError
            title="Connection error"
            subheader="Failed to load majors"
          />
        );
      case "serverError":
        return (
          <ContentLoadError
            title="Server error"
            subheader="Failed to load majors"
          />
        );
    }
  };

  useEffect(() => {
    getMajors();
  }, []);

  return (
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <PanelHeader label="Find your university major" />
      {getContent()}
    </Box>
  );
};

const ChooseTimeRangeSubpanel = (props: ChooseTimeRangeSubpanel) => {
  const [startDate, setStartDate] = useState<Dayjs | null>(null);
  const [endDate, setEndDate] = useState<Dayjs | null>(null);
  const [shouldButtonBeShown, setShouldButtonBeShown] =
    useState<boolean>(false);
  const [wasExchangeAddedSuccessfully, setWasExchangeAddedSuccessfully] =
    useState<boolean>(false);
  const { showAlert } = useSnackbar();

  const handleAddingExchange = async () => {
    if (
      !props.selectedUniversityId ||
      !props.selectedMajorId ||
      !startDate ||
      !endDate
    ) {
      showAlert("An error occured please try again later.", "error");
      return;
    }
    const formattedStartDate = startDate.format("YYYY-MM-DD");
    const formattedEndDate = endDate.format("YYYY-MM-DD");
    const request: CreateExchangeRequest = {
      startedAt: formattedStartDate,
      endAt: formattedEndDate,
      universityId: props.selectedUniversityId,
      fieldOfStudyId: props.selectedMajorId,
    };
    showAlert("Waiting for server response.", "info");
    setShouldButtonBeShown(false);
    const response = await sendCreateExchangeRequest(request);
    if (response.isSuccess) {
      const exchandeDetails = response.data;
      showAlert("Exchange was successfully added.", "success");
      setWasExchangeAddedSuccessfully(true);
      props.setCurrentStage("Exchange added successfully");
      props.setExchangesProps({
        exchanges: [
          {
            id: exchandeDetails.id,
            timeRange: {
              startedAt: exchandeDetails.timeRange.startedAt,
              endAt: exchandeDetails.timeRange.endAt,
            },
            university: {
              id: exchandeDetails.university.id,
              name: exchandeDetails.university.englishName
                ? exchandeDetails.university.englishName
                : exchandeDetails.university.nativeName,
            },
            universityMajorName: exchandeDetails.fieldOfStudy.name,
            city: {
              name: exchandeDetails.university.city.name,
              countryName: exchandeDetails.university.city.country.englishName,
            },
            user: {
              id: exchandeDetails.user.id,
              nick: exchandeDetails.user.nick,
            },
          },
        ],
      });
    } else {
      const globalErrorsCodes = getGlobalErrorCodes(response.error);
      if (globalErrorsCodes.length !== 0) {
        if (globalErrorsCodes.includes("ValidDateRange")) {
          showAlert("Start date must be before end date.", "error");
        } else {
          showAlert("An error occured please try again later.", "error");
        }
      }
    }
  };

  useEffect(() => {
    setShouldButtonBeShown(!!(startDate && endDate));
  }, [startDate, endDate]);

  return (
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <PanelHeader label="Pick boundary dates for your exchange" />
      {!wasExchangeAddedSuccessfully && (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <Box display="flex" gap={2}>
            <DatePicker
              label="Start date"
              value={startDate}
              onChange={(newValue) => setStartDate(newValue)}
            />
            <DatePicker
              label="End date"
              value={endDate}
              onChange={(newValue) => setEndDate(newValue)}
            />
          </Box>
          {shouldButtonBeShown && (
            <Button
              variant="contained"
              size="medium"
              sx={{
                marginLeft: 2,
                marginTop: 2,
                width: { xs: "50%", md: "25%" },
              }}
              onClick={() => {
                handleAddingExchange();
              }}
            >
              Add exchange
            </Button>
          )}
        </LocalizationProvider>
      )}
    </Box>
  );
};

const ExchangeAddedSuccessfullySubpanel = (
  props: ExchangeAddedSuccessfullySubpanelProps,
) => {
  return (
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <PanelHeader label="Added exchange details" />
      <br></br>
      {props.exchangesProps && <Exchanges {...props.exchangesProps} />}
      <Button
        variant="contained"
        size="medium"
        sx={{
          marginLeft: 2,
          marginTop: 4,
          width: { xs: "50%", md: "20%" },
          backgroundColor: "#04315f",
          "&:hover": {
            backgroundColor: "#064080",
          },
        }}
        onClick={() => {
          props.setCurrentStage("Choose university");
        }}
      >
        Add next exchange
      </Button>
    </Box>
  );
};

const AddExchangePanel = () => {
  const [currentStage, setCurrentStage] =
    useState<AddExchangeStage>("Choose university");
  const [selectedUniversityId, setSelectedUniversityId] = useState<
    number | undefined
  >(undefined);
  const [selectedMajorId, setSelectedMajorId] = useState<number | undefined>(
    undefined,
  );
  const [exchangesProps, setExchangesProps] = useState<
    ExchangesProps | undefined
  >(undefined);

  useEffect(() => {
    if (currentStage === "Exchange added successfully") {
      setSelectedUniversityId(undefined);
      setSelectedMajorId(undefined);
    }
  }, [currentStage]);

  const getStagePanel = () => {
    if (currentStage === "Choose university") {
      return (
        <ChooseUniversitySubpanel
          selectedUniversityId={selectedUniversityId}
          setSelectedUniversityId={setSelectedUniversityId}
          setCurrentStage={setCurrentStage}
        />
      );
    } else if (currentStage === "Choose major") {
      return (
        <ChooseMajorSubpanel
          selectedMajorId={selectedMajorId}
          setSelectedMajorId={setSelectedMajorId}
          setCurrentStage={setCurrentStage}
        />
      );
    } else if (currentStage === "Choose time range") {
      return (
        <ChooseTimeRangeSubpanel
          selectedMajorId={selectedMajorId}
          selectedUniversityId={selectedUniversityId}
          setCurrentStage={setCurrentStage}
          setExchangesProps={setExchangesProps}
        />
      );
    } else if (currentStage === "Exchange added successfully") {
      return (
        <ExchangeAddedSuccessfullySubpanel
          exchangesProps={exchangesProps}
          setCurrentStage={setCurrentStage}
        />
      );
    }
  };

  return <Box>{getStagePanel()}</Box>;
};

export default AddExchangePanel;
