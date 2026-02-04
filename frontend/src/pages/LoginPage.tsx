import {
  Box,
  Stack,
  Typography,
  TextField,
  Button,
  Container,
  Divider,
  InputAdornment,
  Paper,
  GlobalStyles,
  CircularProgress,
} from "@mui/material";
import { useEffect, useState } from "react";
import type { LoginRequest } from "../dtos/auth/LoginRequest";
import { sendLoginRequest } from "../utils/api/auth";
import { useNavigate } from "react-router-dom";
import { useSignedInUser } from "../context/SignedInUserContext";
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";
import { sendGetMeRequest } from "../utils/user";

const LoginPage = () => {
  const navigate = useNavigate();
  const [login, setLogin] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [wasLoginAttemptFailed, setWasLoginAttemptFailed] =
    useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string | undefined>(
    undefined,
  );
  const { setSignedInUser } = useSignedInUser();
  const [isWaitingForResponse, setIsWaitingForResponse] =
    useState<boolean>(false);

  const handleLogin = async () => {
    if (login.trim() === "" || password.trim() === "") {
      setErrorMessage("Please fill in both login and password fields.");
      setWasLoginAttemptFailed(true);
      return;
    }
    setIsWaitingForResponse(true);
    const body: LoginRequest = {
      login,
      password,
    };
    const result = await sendLoginRequest(body);
    if (result.isSuccess) {
      const resultMe = await sendGetMeRequest();
      if (resultMe.isSuccess) {
        const userId = resultMe.data.id;
        setSignedInUser({
          id: userId,
          avatarUrl: resultMe.data.avatarUrl,
          isSignedIn: true,
        });
        setWasLoginAttemptFailed(false);
        navigate(`/users/${userId}`);
      } else {
        setErrorMessage(
          "Server error occured while fetching data. Please try again later.",
        );
      }
    } else {
      setPassword(() => "");
      setWasLoginAttemptFailed(true);
      switch (result.error.status) {
        case "UNAUTHORIZED":
          setErrorMessage("Invalid credentials.");
          break;
        case "INTERNAL_SERVER_ERROR":
          setErrorMessage("Server error occured. Please try again later.");
          break;
        case "SERVICE_UNAVAILABLE":
          setErrorMessage("Connection error. Please try again later.");
          break;
      }
    }
    setIsWaitingForResponse(false);
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      handleLogin();
    }
  };

  useEffect(() => {
    setTimeout(() => {
      setWasLoginAttemptFailed(false);
      setErrorMessage(undefined);
    }, 10000);
  }, [wasLoginAttemptFailed]);

  return (
    <>
      <GlobalStyles
        styles={{ body: { backgroundColor: "#0f1c2e", overflowX: "hidden" } }}
      />
      <Box
        sx={{
          position: "fixed",
          top: 0,
          left: 0,
          width: "100%",
          height: "100%",
          overflowY: "auto",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          background: "linear-gradient(135deg, #182c44 0%, #0f1c2e 100%)",
          padding: 2,
        }}
      >
        <Container maxWidth="xs">
          <Paper
            elevation={10}
            sx={{
              padding: { xs: 3, sm: 5 },
              borderRadius: 4,
              backgroundColor: "rgba(255, 255, 255, 0.98)",
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              animation: "fadeIn 0.5s ease-in-out",
              "@keyframes fadeIn": {
                "0%": { opacity: 0, transform: "translateY(20px)" },
                "100%": { opacity: 1, transform: "translateY(0)" },
              },
            }}
          >
            <Stack
              spacing={1}
              sx={{ mb: 4, textAlign: "center", width: "100%" }}
            >
              <Typography
                variant="h4"
                component="h1"
                sx={{
                  fontWeight: 800,
                  color: "#182c44",
                  fontSize: { xs: "1.5rem", sm: "2rem" },
                  letterSpacing: "-0.5px",
                }}
              >
                Welcome Back
              </Typography>
              <Typography variant="body2" color="text.secondary">
                We are happy to see you again
              </Typography>
            </Stack>
            <Stack spacing={3} sx={{ width: "100%" }}>
              <TextField
                value={login}
                onChange={(e) => setLogin(e.target.value)}
                onKeyDown={handleKeyDown}
                id="login-input"
                label="Login"
                variant="outlined"
                fullWidth
                autoComplete="username"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <PersonOutlineIcon color="action" />
                    </InputAdornment>
                  ),
                }}
              />
              <TextField
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onKeyDown={handleKeyDown}
                id="password-input"
                type="password"
                label="Password"
                variant="outlined"
                fullWidth
                autoComplete="current-password"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <LockOutlinedIcon color="action" />
                    </InputAdornment>
                  ),
                }}
              />
              {wasLoginAttemptFailed && (
                <Stack
                  direction="row"
                  alignItems="center"
                  spacing={1}
                  sx={{
                    backgroundColor: "#ffebee",
                    padding: 1.5,
                    borderRadius: 2,
                    color: "#d32f2f",
                    border: "1px solid #ffcdd2",
                  }}
                >
                  <ErrorOutlineIcon fontSize="small" />
                  <Typography variant="caption" fontWeight={600}>
                    {errorMessage}
                  </Typography>
                </Stack>
              )}
              <Box sx={{ position: "relative", mt: 2 }}>
                <Button
                  variant="contained"
                  size="large"
                  onClick={handleLogin}
                  fullWidth
                  disabled={isWaitingForResponse}
                  sx={{
                    py: 1.5,
                    mt: 1,
                    backgroundColor: "#182c44",
                    fontWeight: "bold",
                    textTransform: "none",
                    fontSize: "1rem",
                    boxShadow: "0 4px 12px rgba(24, 44, 68, 0.3)",
                    "&:hover": {
                      backgroundColor: "#2c4866",
                      boxShadow: "0 6px 16px rgba(24, 44, 68, 0.4)",
                    },
                  }}
                >
                  Sign In
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
            <Box sx={{ mt: 4, width: "100%" }}>
              <Divider sx={{ mb: 2 }}>
                <Typography variant="caption" color="text.secondary">
                  OR
                </Typography>
              </Divider>
              <Stack
                direction="row"
                justifyContent="center"
                alignItems="center"
                spacing={1}
              >
                <Typography variant="body2" color="text.secondary">
                  Don't have an account?
                </Typography>
                <Button
                  variant="text"
                  onClick={() => navigate("/register")}
                  sx={{
                    textTransform: "none",
                    fontWeight: 700,
                    color: "#182c44",
                  }}
                >
                  Create Account
                </Button>
              </Stack>
            </Box>
          </Paper>
        </Container>
      </Box>
    </>
  );
};

export default LoginPage;
