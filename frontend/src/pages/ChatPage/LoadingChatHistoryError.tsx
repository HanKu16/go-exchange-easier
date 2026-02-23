import { Box, Button, Typography } from "@mui/material";
import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";

const LoadingChatHistoryError = () => {
  return (
    <Box
      sx={{
        flexGrow: 1,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        gap: 2,
        p: 4,
        textAlign: "center",
        backgroundColor: "#dedede",
        width: "100%",
      }}
    >
      <ErrorOutlineIcon sx={{ fontSize: 60, color: "#a14949", opacity: 0.8 }} />
      <Box>
        <Typography variant="h6" sx={{ color: "#333", fontWeight: 600 }}>
          Oops! Something went wrong
        </Typography>
        <Typography variant="body2" sx={{ color: "text.secondary", mt: 0.5 }}>
          We couldn't load the chat history. Please try again.
        </Typography>
      </Box>
      <Button
        variant="contained"
        // onClick={() => fetchNextPage()}
        sx={{
          mt: 1,
          backgroundColor: "#a14949",
          color: "white",
          px: 4,
          py: 1,
          borderRadius: "20px",
          textTransform: "none",
          fontWeight: 600,
          "&:hover": {
            backgroundColor: "#c33a3a",
          },
        }}
      >
        Try Again
      </Button>
    </Box>
  );
};

export default LoadingChatHistoryError;
