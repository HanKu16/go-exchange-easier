import { Box } from "@mui/material";
import ConversationBox from "./ConservationBox";

const ConversationList = () => {
  return (
    <Box
      sx={{
        width: { xs: "100%", md: "30%", lg: "25%" },
        height: "100%",
        display: "flex",
        flexDirection: "column",
        backgroundColor: "#a5a2a2",
        gap: 1,
        alignItems: "center",
        paddingY: 2,
        overflowY: "auto",
        borderRight: "1px solid #e0e0e0",
      }}
    >
      {[...Array(15)].map((_, i) => (
        <ConversationBox key={i} />
      ))}
    </Box>
  );
};

export default ConversationList;
