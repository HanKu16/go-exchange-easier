import { Avatar, Box } from "@mui/material";
import type { MessageBoxProps } from "./types";
import basicAvatar from "../../assets/basic-avatar.png";
import dayjs from "dayjs";

const MessageBox = (props: MessageBoxProps) => {
  return (
    <Box
      sx={{
        marginY: 1,
        width: "100%",
        flexDirection: "row",
        display: "flex",
        justifyContent: props.isUserMessage ? "flex-end" : "flex-start",
      }}
    >
      <Box sx={{ display: "flex", maxWidth: "60%", alignItems: "flex-end" }}>
        {!props.isUserMessage && (
          <Avatar
            alt="User avatar"
            src={props.avatarUrl || basicAvatar}
            sx={{ width: 30, height: 30, marginRight: 1 }}
          />
        )}
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: props.isUserMessage ? "flex-end" : "flex-start",
          }}
        >
          <Box sx={{ fontSize: 13, paddingX: 1, paddingBottom: 0.25 }}>
            {dayjs(props.dateAndTime).isSame(dayjs(), "day")
              ? dayjs(props.dateAndTime).format("HH:mm")
              : dayjs(props.dateAndTime).format("DD.MM.YYYY HH:mm")}
          </Box>
          <Box
            sx={{
              backgroundColor: props.isUserMessage ? "#f4f2f2" : "#e4e0e0",
              borderRadius: 2,
              padding: 0.75,
            }}
          >
            {props.textContent}
          </Box>
        </Box>
      </Box>
    </Box>
  );
};

export default MessageBox;
