import { Avatar, Box, Card, CardContent, Typography } from "@mui/material";
import basicAvatar from "../../assets/basic-avatar.png";

const ConversationBox = () => {
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
        <Box>
          <Avatar
            alt="User avatar"
            src={basicAvatar}
            sx={{ width: 50, height: 50 }}
          />
        </Box>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            marginLeft: 2,
            flexGrow: 1,
            overflow: "hidden",
          }}
        >
          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              justifyContent: "space-between",
              width: "100%",
              mb: 0.5,
            }}
          >
            <Typography variant="subtitle1" fontWeight="bold" noWrap>
              Conversation name
            </Typography>
            <Typography
              variant="caption"
              color="text.secondary"
              sx={{ whiteSpace: "nowrap", ml: 1 }}
            >
              18.02 12:45
            </Typography>
          </Box>
          <Typography variant="body2" color="text.secondary" noWrap>
            Last message preview description...
          </Typography>
        </Box>
      </CardContent>
    </Card>
  );
};

export default ConversationBox;
