import { Box, Typography } from "@mui/material";
import Navbar from "../components/Navbar";
import CloudOffIcon from "@mui/icons-material/CloudOff";
import { useSignedInUser } from "../context/SignedInUserContext";

const ServiceUnavailablePage = () => {
  const { signedInUser } = useSignedInUser();

  return (
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: "#eeececff",
        display: "flex",
        flexDirection: "column",
      }}
    >
      {signedInUser.isSignedIn !== undefined && <Navbar />}
      <Box
        sx={{
          flexGrow: 1,
          p: 4,
          textAlign: "center",
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <CloudOffIcon sx={{ fontSize: 120, color: "error.main", mb: 3 }} />
        <Typography variant="h3" component="h1" gutterBottom>
          Service is currently unavailable
        </Typography>
        <Typography variant="h6" color="text.secondary" sx={{ mb: 4 }}>
          Please try again later
        </Typography>
      </Box>
    </Box>
  );
};

export default ServiceUnavailablePage;
