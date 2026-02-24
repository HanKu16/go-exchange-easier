import { Box } from "@mui/material";
import type { TemporaryMessageBoxProps } from "./types";
import dayjs from "dayjs";

const TemporaryMessageBox = ({ message }: TemporaryMessageBoxProps) => {
  return (
    <Box
      sx={{
        marginY: 1,
        width: "100%",
        flexDirection: "row",
        display: "flex",
        justifyContent: "flex-end",
      }}
    >
      <Box sx={{ display: "flex", maxWidth: "60%", alignItems: "flex-end" }}>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-end",
          }}
        >
          <Box sx={{ fontSize: 13, paddingX: 1, paddingBottom: 0.25 }}>
            {dayjs(message.createdAt).isSame(dayjs(), "day")
              ? dayjs(message.createdAt).format("HH:mm")
              : dayjs(message.createdAt).format("DD.MM.YYYY HH:mm")}
          </Box>
          <Box
            sx={{
              backgroundColor: "#f4f2f2",
              borderRadius: 2,
              padding: 0.75,
            }}
          >
            {message.textContent}
          </Box>
          {message.isSuccessfullySend === false && (
            <Box sx={{ color: "#f08585", fontSize: "0.8rem" }}>
              Failed to send message
            </Box>
          )}
        </Box>
      </Box>
    </Box>
  );
};

export default TemporaryMessageBox;
