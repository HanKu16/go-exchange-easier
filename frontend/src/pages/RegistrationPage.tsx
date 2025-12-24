import {
  Grid,
  Box,
  Container,
  Typography,
  TextField,
  Alert,
  FormControlLabel,
  Checkbox,
  Link,
  Button,
  CircularProgress,
} from "@mui/material";
import { useTheme, useMediaQuery } from "@mui/material";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import InfoIcon from "@mui/icons-material/Info";
import goExchangeEasierCaptionImage from "../assets/registration_page/caption.png";
import earthImage from "../assets/registration_page/earth.png";
import registrationPageTextImage from "../assets/registration_page/text.png";
import registrationSuccessImage from "../assets/registration_page/registration-success.png";
import type { UserRegistrationRequest } from "../dtos/auth/UserRegistrationRequest";
import { useState } from "react";
import { sendUserRegistrationRequest } from "../utils/auth";
import type { ApiErrorResponseCode } from "../dtos/error/ApiErrorResponseCode";
import type { ApiErrorResponse } from "../dtos/error/ApiErrorResponse";
import { useNavigate } from "react-router-dom";

type InputFieldProps = {
  id: string;
  label: string;
  infoText: string;
  isRequired: boolean;
  value: string;
  setValue: (value: string) => void;
};

const InputField = (props: InputFieldProps) => {
  return (
    <Box sx={{ display: "flex", width: "100%", justifyContent: "center" }}>
      <TextField
        id={props.id}
        label={props.label}
        required={props.isRequired}
        placeholder={`Enter your ${props.label.toLocaleLowerCase()}`}
        autoComplete="off"
        value={props.value}
        onChange={(e) => props.setValue(e.target.value)}
        sx={{
          width: { xs: "80%", sm: "60%", md: "50%" },
          paddingRight: { lg: 1 },
        }}
      />
      <Tooltip title={props.infoText}>
        <IconButton>
          <InfoIcon />
        </IconButton>
      </Tooltip>
    </Box>
  );
};

const SuccessfulRegistrationPanel = () => {
  const navigate = useNavigate();
  return (
    <Container
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        height: "100%",
        paddingY: { xs: 7, sm: 12, lg: 10 },
      }}
    >
      <Typography
        sx={{ fontSize: { xs: "4rem", sm: "5rem", md: "6rem", lg: "3rem" } }}
      >
        Welcome!
      </Typography>
      <Box sx={{ height: { xs: "40%", sm: "50%", md: "60%", lg: "40%" } }}>
        <img
          src={registrationSuccessImage}
          alt="Registration Success Image"
          style={{ height: "100%", objectFit: "contain" }}
        />
      </Box>
      <Typography sx={{ fontSize: { sm: "2rem", lg: "1.5rem" } }}>
        Your account was successfully created
      </Typography>
      <Button
        variant="contained"
        size="large"
        onClick={() => navigate(`/login`)}
        sx={{ marginTop: { xs: 3, md: 4, lg: 3 }, marginBottom: 2 }}
      >
        Sign In
      </Button>
    </Container>
  );
};

export const RegistrationPage = () => {
  const theme = useTheme();
  const isLgScreen = useMediaQuery(theme.breakpoints.up("lg"));
  const [login, setLogin] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [nick, setNick] = useState<string>("");
  const [mail, setMail] = useState<string>("");
  const [isWaitingForResponse, setIsWaitingForResponse] =
    useState<boolean>(false);
  const [wasRegistrationSuccessful, setWasRegistrationSuccessful] =
    useState<boolean>(false);
  const [isAgreementAccepted, setIsAgreementAccepted] =
    useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleError = (error: ApiErrorResponse) => {
    const errorFieldNames: string[] = [
      ...new Set(error.fieldErrors.map((e) => e.field)),
    ];
    const globalErrorsCodes: ApiErrorResponseCode[] = error.globalErrors.map(
      (e) => e.code
    );
    if (errorFieldNames.length != 0) {
      setErrorMessage(
        "Registration attempt failed. Check requirments for " +
          errorFieldNames.join(", ") +
          "."
      );
    } else if (globalErrorsCodes.includes("LoginAlreadyTaken")) {
      setErrorMessage("Login is already taken.");
    } else if (globalErrorsCodes.includes("MailAlreadyTaken")) {
      setErrorMessage("Account associated with given name already exists.");
    } else if (
      globalErrorsCodes.includes("InternalError") ||
      error.status === "INTERNAL_SERVER_ERROR"
    ) {
      setErrorMessage("An unexpected error occured. Please try again later.");
    }
  };

  const handleRegistration = async () => {
    setErrorMessage(null);
    if (!isAgreementAccepted) {
      setErrorMessage(
        "Terms and Conditions must be accepted if you want to sign up"
      );
      return;
    }
    setIsWaitingForResponse(true);
    const request: UserRegistrationRequest = {
      login,
      password,
      nick,
      mail,
    };
    const result = await sendUserRegistrationRequest(request);
    setIsWaitingForResponse(false);
    if (result.isSuccess) {
      setWasRegistrationSuccessful(true);
    } else {
      handleError(result.error);
    }
  };

  return (
    <Grid container spacing={2}>
      <Grid size={{ xs: 12, lg: 7 }}>
        <Container
          sx={{
            backgroundColor: "#182c44",
            height: { xs: "10vh", lg: "100vh" },
            display: "flex",
            flexDirection: "column",
            paddingTop: 2,
          }}
        >
          {isLgScreen ? (
            <>
              <img
                src={goExchangeEasierCaptionImage}
                alt="Go Exchange Easier Caption"
                style={{ height: "20%", objectFit: "contain" }}
              />
              <img
                src={earthImage}
                alt="Earth"
                style={{
                  height: "50%",
                  objectFit: "contain",
                  marginBottom: 20,
                }}
              />
              <img
                src={registrationPageTextImage}
                alt="Find people that already were in places you want to go
                    and pick the best one for you"
                style={{ height: "20%", objectFit: "contain" }}
              />
            </>
          ) : (
            <>
              <img
                src={goExchangeEasierCaptionImage}
                alt="Go Exchange Easier Caption"
                style={{
                  height: "90%",
                  objectFit: "contain",
                  paddingBottom: 3,
                  transform: "rotate(3deg)",
                }}
              />
            </>
          )}
        </Container>
      </Grid>
      <Grid size={{ xs: 12, lg: 5 }}>
        {wasRegistrationSuccessful ? (
          <SuccessfulRegistrationPanel />
        ) : (
          <Container
            sx={{
              backgroundColor: "white",
              height: "100vh",
              paddingY: { lg: 3 },
            }}
          >
            <Box sx={{ display: "flex", justifyContent: "center" }}>
              <Typography
                sx={{
                  fontSize: { xs: "1.5rem", sm: "2.3rem", xl: "2rem" },
                  paddingTop: { xs: 2, sm: 2, xl: 3 },
                }}
              >
                REGISTRATION FORM
              </Typography>
            </Box>
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "space-around",
                height: { xs: "55%", lg: "60%" },
                paddingTop: { xs: 3, lg: 4 },
              }}
            >
              <InputField
                id="login"
                label="Login"
                isRequired={true}
                infoText="Should have between 6 to 20 characters. Only letters and numbers."
                value={login}
                setValue={setLogin}
              />
              <InputField
                id="password"
                label="Password"
                isRequired={true}
                infoText="Should have between 8 to 20 characters. Only letters and numbers."
                value={password}
                setValue={setPassword}
              />
              <InputField
                id="nick"
                label="Nick"
                isRequired={true}
                infoText="Should have between 3 to 20 characters. Only letters and numbers. 
                  Your nick will be shown on your profile. If not provided your login will be used."
                value={nick}
                setValue={setNick}
              />
              <InputField
                id="mail"
                label="Mail"
                isRequired={false}
                infoText="Should be a valid email. By giving email you let us notify 
                  you about new message in the service."
                value={mail}
                setValue={setMail}
              />
            </Box>
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                paddingTop: { lg: 2 },
              }}
            >
              <FormControlLabel
                control={
                  <Checkbox
                    value={isAgreementAccepted}
                    onChange={() =>
                      setIsAgreementAccepted((prevState) => !prevState)
                    }
                  />
                }
                label={
                  <Box
                    sx={{
                      fontSize: {
                        xs: "0.7rem",
                        sm: "1.2rem",
                        md: "1.3rem",
                        lg: "0.8rem",
                      },
                    }}
                  >
                    <span>I read and agree to </span>
                    <Link
                      href="/terms-and-conditions"
                      target="_blank"
                      rel="noopener"
                      sx={{ fontWeight: "bold" }}
                      onClick={() => {}}
                    >
                      Terms and Conditions
                    </Link>
                  </Box>
                }
              />
              {isWaitingForResponse ? (
                <CircularProgress sx={{ marginTop: 2 }} />
              ) : (
                <Button
                  variant="contained"
                  onClick={handleRegistration}
                  sx={{
                    marginTop: { xs: 1.5, lg: 2 },
                    marginBottom: 2,
                  }}
                >
                  Sign Up
                </Button>
              )}
              {errorMessage != null ? (
                <Alert severity="error" variant="filled">
                  {errorMessage}
                </Alert>
              ) : (
                <></>
              )}
            </Box>
          </Container>
        )}
      </Grid>
    </Grid>
  );
};

export default RegistrationPage;
