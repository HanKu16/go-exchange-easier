import { Box } from "@mui/material";
import Navbar from "../../components/Navbar";
import ConversationList from "./ConversationList";

const ChatPage = () => {
  return (
    <Box
      sx={{
        display: "flex",
        height: "100vh",
        flexDirection: "column",
        overflow: "hidden",
      }}
    >
      <Navbar />
      <Box
        sx={{
          display: "flex",
          flexGrow: 1,
          overflow: "hidden",
          flexDirection: { xs: "column", md: "row" },
        }}
      >
        <ConversationList />
        <Box
          sx={{
            flexGrow: 1,
            display: { xs: "none", md: "flex" },
            flexDirection: "column",
            backgroundColor: "#fff",
            width: { md: "70%", lg: "75%" },
            justifyContent: "center",
            alignItems: "center",
          }}
        ></Box>
      </Box>
    </Box>
  );
};

export default ChatPage;
