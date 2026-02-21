import { Box, Card, Typography, alpha } from "@mui/material";
import ErrorTwoToneIcon from "@mui/icons-material/ErrorTwoTone";

const ErrorRoomsBox = () => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        minHeight: "300px",
        width: "100%",
        p: 3,
      }}
    >
      <Card
        sx={{
          width: "100%",
          maxWidth: 400,
          p: 4,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          borderRadius: 4,
          boxShadow: "0 8px 24px rgba(211, 47, 47, 0.05)",
          background: "linear-gradient(145deg, #ffffff 0%, #fffefe 100%)",
          border: "1px solid #fee2e2",
        }}
      >
        <Box
          sx={{
            width: 80,
            height: 80,
            borderRadius: "50%",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            bgcolor: (theme) => alpha(theme.palette.error.main, 0.1),
            color: "error.main",
            mb: 2,
          }}
        >
          <ErrorTwoToneIcon sx={{ fontSize: 40 }} />
        </Box>

        <Typography
          variant="h6"
          sx={{ fontWeight: 700, color: "#c62828", mb: 1 }}
        >
          Something went wrong
        </Typography>

        <Typography
          variant="body2"
          sx={{
            textAlign: "center",
            color: "#7f8c8d",
            mb: 3,
            lineHeight: 1.6,
          }}
        >
          We couldn't load your conversations. Please check your internet
          connection and try again.
        </Typography>
      </Card>
    </Box>
  );
};

export default ErrorRoomsBox;
