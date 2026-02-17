import { Typography } from "@mui/material";

const PageHeaders = () => (
  <>
    <Typography
      variant="h3"
      component="h1"
      fontWeight="800"
      sx={{ mb: 1, textAlign: "center" }}
    >
      Explore our community
    </Typography>
    <Typography
      variant="body1"
      color="text.secondary"
      sx={{ mb: 5, textAlign: "center", maxWidth: 500 }}
    >
      Find people and universities that match your exchange preferences
    </Typography>
  </>
);

export default PageHeaders;
