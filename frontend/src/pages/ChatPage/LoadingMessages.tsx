import { Box, CircularProgress } from "@mui/material";

const LoadingMessages = () => {
  return (
    <Box
      sx={{
        flex: 1,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
        color: "text.secondary",
        p: 3,
      }}
    >
      <CircularProgress size={15} sx={{ color: "#1f3958ff" }} />
    </Box>
  );
};

export default LoadingMessages;
