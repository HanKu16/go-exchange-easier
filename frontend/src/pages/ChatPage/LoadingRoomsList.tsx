import { Box, Card, CardContent } from "@mui/material";

const LoadingRoomBox = () => {
  return (
    <Card
      sx={{
        width: "95%",
        height: "5rem",
        mb: 1,
        flexShrink: 0,
        cursor: "pointer",
        "&:hover": { backgroundColor: "#f5f5f5" },
      }}
    >
      <CardContent
        sx={{
          padding: 2,
          display: "flex",
          flexDirection: "row",
          alignItems: "center",
          height: "100%",
          "&:last-child": { paddingBottom: 2 },
        }}
      >
        <Box
          sx={{ bgcolor: "#d1d1d1", width: 50, height: 50, borderRadius: 25 }}
        />
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-around",
            marginLeft: 2,
            flexGrow: 1,
            overflow: "hidden",
            height: "100%",
          }}
        >
          <Box
            sx={{
              bgcolor: "#d1d1d1",
              width: "40%",
              height: 15,
              borderRadius: 18,
            }}
          />
          <Box
            sx={{
              bgcolor: "#d1d1d1",
              width: "90%",
              height: 13,
              borderRadius: 15,
            }}
          />
        </Box>
      </CardContent>
    </Card>
  );
};

const LoadingRoomsList = ({ numberOfBoxes }: { numberOfBoxes: number }) => {
  return (
    <>
      {[...Array(numberOfBoxes)].map((_) => (
        <LoadingRoomBox />
      ))}
    </>
  );
};

export default LoadingRoomsList;
