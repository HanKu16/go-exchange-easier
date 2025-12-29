import { Box, Typography } from "@mui/material";
import SearchOffIcon from "@mui/icons-material/SearchOff";

export type ContentEmptyProps = {
  title: string;
  subheader: string;
};

const ContentEmpty = (props: ContentEmptyProps) => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        minHeight: 200,
        textAlign: "center",
        color: "text.secondary",
        p: 3,
      }}
    >
      <SearchOffIcon sx={{ fontSize: 60, mb: 2, color: "text.disabled" }} />
      <Typography variant="h6" gutterBottom>
        {props.title}
      </Typography>
      <Typography variant="body2">{props.subheader}</Typography>
    </Box>
  );
};

export default ContentEmpty;
