import { Box, CircularProgress, Typography } from "@mui/material";
import captionImage from "../assets/caption.png";

type LoadingPageProps = {
  backgroundColor: string;
  circularProgressColor: string;
  text: string;
};

const LoadingPage = (props: LoadingPageProps) => {
  return (
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: props.backgroundColor,
        display: "flex",
        flexDirection: "column",
      }}
    >
      <Box
        sx={{
          flexGrow: 1,
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          p: 6,
        }}
      >
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            maxWidth: "500px",
            width: "100%",
          }}
        >
          <img
            src={captionImage}
            alt="Go Exchange Easier Caption"
            style={{
              width: "100%",
              objectFit: "contain",
              marginBottom: "50px",
            }}
          />
          <CircularProgress
            size={55}
            sx={{ color: props.circularProgressColor, marginBottom: "40px" }}
          />
          <Typography
            variant="h6"
            sx={{ color: props.circularProgressColor, textAlign: "center" }}
          >
            {props.text}
          </Typography>
        </Box>
      </Box>
    </Box>
  );
};

export default LoadingPage;
