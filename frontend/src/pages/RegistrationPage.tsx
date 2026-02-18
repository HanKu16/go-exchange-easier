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
  InputAdornment,
  Paper,
  Stack,
  GlobalStyles,
} from "@mui/material";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import InfoIcon from "@mui/icons-material/Info";
import goExchangeEasierCaptionImage from "../assets/caption.png";
import earthImage from "../assets/earth.png";
import registrationPageTextImage from "../assets/text.png";
import registrationSuccessImage from "../assets/success.png";
import type { UserRegistrationRequest } from "../dtos/auth/UserRegistrationRequest";
import { useState } from "react";
import { sendRegistrationRequest } from "../utils/api/auth";
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
  type?: string;
};

const InputField = (props: InputFieldProps) => {
  return (
    <TextField
      id={props.id}
      label={props.label}
      required={props.isRequired}
      placeholder={`Enter your ${props.label.toLocaleLowerCase()}`}
      autoComplete="off"
      value={props.value}
      onChange={(e) => props.setValue(e.target.value)}
      type={props.type || "text"}
      fullWidth
      variant="outlined"
      InputProps={{
        endAdornment: (
          <InputAdornment position="end">
            <Tooltip title={props.infoText} arrow placement="top">
              <IconButton edge="end" size="small">
                <InfoIcon color="action" />
              </IconButton>
            </Tooltip>
          </InputAdornment>
        ),
      }}
    />
  );
};

const SuccessfulRegistrationPanel = () => {
  const navigate = useNavigate();
  return (
    <Container
      maxWidth="sm"
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        minHeight: "100vh",
        textAlign: "center",
        py: 4,
      }}
    >
      <Paper
        elevation={3}
        sx={{ p: 5, borderRadius: 3, backgroundColor: "white" }}
      >
        <Typography
          variant="h3"
          component="h1"
          gutterBottom
          sx={{ fontWeight: "bold", color: "#182c44" }}
        >
          Welcome!
        </Typography>

        <Box sx={{ maxWidth: "300px", width: "100%", my: 4, mx: "auto" }}>
          <img
            src={registrationSuccessImage}
            alt="Registration Success"
            style={{ width: "100%", height: "auto", objectFit: "contain" }}
          />
        </Box>
        <Typography variant="h6" color="text.secondary" gutterBottom>
          Your account was successfully created
        </Typography>
        <Button
          variant="contained"
          size="large"
          onClick={() => navigate(`/login`)}
          sx={{
            mt: 4,
            px: 6,
            py: 1.5,
            fontSize: "1.1rem",
            borderRadius: 2,
            textTransform: "none",
            backgroundColor: "#182c44",
            "&:hover": { backgroundColor: "#122133" },
          }}
        >
          Sign In
        </Button>
      </Paper>
    </Container>
  );
};

export const RegistrationPage = () => {
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
      (e) => e.code,
    );
    if (errorFieldNames.length != 0) {
      setErrorMessage(
        "Registration attempt failed. Check requirements for " +
          errorFieldNames.join(", ") +
          ".",
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
    } else if (error.status === "SERVICE_UNAVAILABLE") {
      setErrorMessage("Connection error occured. Please try again later.");
    }
  };

  const handleRegistration = async () => {
    setErrorMessage(null);
    if (!isAgreementAccepted) {
      setErrorMessage(
        "Terms and Conditions must be accepted if you want to sign up",
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
    const result = await sendRegistrationRequest(request);
    setIsWaitingForResponse(false);
    if (result.isSuccess) {
      setWasRegistrationSuccessful(true);
    } else {
      handleError(result.error);
    }
  };

  return (
    <>
      <GlobalStyles
        styles={{ body: { backgroundColor: "#0f1c2e", overflowX: "hidden" } }}
      />
      <Grid
        container
        component="main"
        sx={{ minHeight: "100vh", backgroundColor: "#182c44" }}
      >
        <Grid
          size={{ lg: 7 }}
          sx={{
            display: { xs: "none", lg: "flex" },
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            p: 4,
            height: "100vh",
            position: "sticky",
            top: 0,
          }}
        >
          <Stack spacing={4} alignItems="center" sx={{ maxWidth: "80%" }}>
            <img
              src={goExchangeEasierCaptionImage}
              alt="Go Exchange Easier"
              style={{
                maxWidth: "100%",
                maxHeight: "15vh",
                objectFit: "contain",
              }}
            />
            <img
              src={earthImage}
              alt="Earth"
              style={{
                maxWidth: "100%",
                maxHeight: "40vh",
                objectFit: "contain",
                animation: "float 6s ease-in-out infinite",
              }}
            />
            <img
              src={registrationPageTextImage}
              alt="Find people..."
              style={{
                maxWidth: "100%",
                maxHeight: "15vh",
                objectFit: "contain",
              }}
            />
          </Stack>
        </Grid>
        <Grid
          size={{ xs: 12, lg: 5 }}
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            py: 2,
            minHeight: { lg: "100vh" },
          }}
        >
          {wasRegistrationSuccessful ? (
            <SuccessfulRegistrationPanel />
          ) : (
            <Container maxWidth="sm">
              <Box
                sx={{
                  display: { xs: "block", lg: "none" },
                  textAlign: "center",
                  mb: 4,
                  mt: { xs: 2, sm: 4 },
                }}
              >
                <img
                  src={goExchangeEasierCaptionImage}
                  alt="Go Exchange Easier"
                  style={{ maxWidth: "70%", height: "auto" }}
                />
              </Box>
              <Paper
                elevation={3}
                sx={{
                  p: { xs: 3, sm: 5 },
                  borderRadius: 3,
                  backgroundColor: "white",
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "center",
                }}
              >
                <Typography
                  component="h1"
                  variant="h4"
                  sx={{ mb: 3, fontWeight: 700, color: "#182c44" }}
                >
                  Sign Up
                </Typography>
                <Stack spacing={2.5} width="100%">
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
                    type="password"
                  />
                  <InputField
                    id="nick"
                    label="Nick"
                    isRequired={true}
                    infoText="Your nick will be shown on your profile. If not provided your login will be used. 3-20 chars."
                    value={nick}
                    setValue={setNick}
                  />
                  <InputField
                    id="mail"
                    label="Email"
                    isRequired={false}
                    infoText="Should be a valid email. We use this to notify you about new messages."
                    value={mail}
                    setValue={setMail}
                  />

                  <FormControlLabel
                    control={
                      <Checkbox
                        value={isAgreementAccepted}
                        color="primary"
                        onChange={() => setIsAgreementAccepted((prev) => !prev)}
                      />
                    }
                    label={
                      <Typography variant="body2" color="text.secondary">
                        I read and agree to the{" "}
                        <Link
                          href="/terms-and-conditions"
                          target="_blank"
                          rel="noopener"
                          underline="hover"
                          sx={{ fontWeight: "bold", color: "#182c44" }}
                          onClick={(e) => e.stopPropagation()}
                        >
                          Terms and Conditions
                        </Link>
                      </Typography>
                    }
                    sx={{ alignItems: "center", mt: 1 }}
                  />
                  {errorMessage && (
                    <Alert severity="error" sx={{ width: "100%" }}>
                      {errorMessage}
                    </Alert>
                  )}
                  <Box sx={{ position: "relative", mt: 2 }}>
                    <Button
                      fullWidth
                      variant="contained"
                      size="large"
                      onClick={handleRegistration}
                      disabled={isWaitingForResponse}
                      sx={{
                        py: 1.5,
                        fontSize: "1rem",
                        fontWeight: "bold",
                        backgroundColor: "#182c44",
                        "&:hover": { backgroundColor: "#122133" },
                      }}
                    >
                      Sign Up
                    </Button>
                    {isWaitingForResponse && (
                      <CircularProgress
                        size={24}
                        sx={{
                          position: "absolute",
                          top: "50%",
                          left: "50%",
                          marginTop: "-12px",
                          marginLeft: "-12px",
                        }}
                      />
                    )}
                  </Box>
                </Stack>
              </Paper>
              <Box sx={{ mt: 3, textAlign: "center" }}>
                <Typography
                  variant="body2"
                  sx={{ color: "rgba(255,255,255,0.7)" }}
                >
                  Already have an account?{" "}
                  <Link
                    href="/login"
                    underline="hover"
                    sx={{ fontWeight: "bold", color: "white" }}
                  >
                    Sign in
                  </Link>
                </Typography>
              </Box>
            </Container>
          )}
        </Grid>
      </Grid>
    </>
  );
};

export default RegistrationPage;
