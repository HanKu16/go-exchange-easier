import React, { useState } from "react";
import { TextField, IconButton, InputAdornment, Paper } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import { Box } from "@mui/material";
import { useParams } from "react-router-dom";
import useChatSync from "../hooks/useChatSync";
import { useSendMessage } from "../hooks/useSendMessage";
import type { MessageInputProps } from "../types";

const MessageInput = (props: MessageInputProps) => {
  const [message, setMessage] = useState("");
  const { roomId } = useParams();
  const { syncAll } = useChatSync(roomId!);
  const { sendMessage } = useSendMessage(roomId!, syncAll);

  const handleSend = () => {
    if (message.trim()) {
      sendMessage(message);
      setMessage("");
    }
  };

  const handleKeyPress = (event: React.KeyboardEvent) => {
    if (event.key === "Enter" && !event.shiftKey) {
      event.preventDefault();
      handleSend();
    }
  };

  return (
    <Box
      sx={{
        flexShrink: 0,
        width: "100%",
        backgroundColor: "#f5f5f5",
        borderTop: "1px solid #ccc",
        p: 1,
      }}
    >
      <Paper
        elevation={3}
        sx={{
          p: "10px 16px",
          backgroundColor: "#f5f5f5",
          borderTop: "1px solid #e0e0e0",
          display: "flex",
          alignItems: "center",
        }}
      >
        <TextField
          fullWidth
          multiline
          maxRows={4}
          placeholder="Write message..."
          variant="outlined"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyDown={handleKeyPress}
          disabled={props.disabled}
          sx={{
            backgroundColor: "white",
            borderRadius: 1,
            "& .MuiOutlinedInput-root": {
              "& fieldset": { borderColor: "#e0e0e0" },
            },
          }}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  onClick={handleSend}
                  disabled={!message.trim() || props.disabled}
                  sx={{
                    color: "#1976d2",
                    transition: "all 0.2s ease",

                    "&:hover": {
                      backgroundColor: "rgba(25, 118, 210, 0.08)",
                    },
                    "&:active": {
                      color: "#0d47a1 !important",
                      transform: "scale(0.85)",
                    },
                    "& .MuiSvgIcon-root": {
                      color: "inherit",
                    },
                    "&.Mui-disabled": {
                      color: "#bdbdbd !important",
                    },
                  }}
                >
                  <SendIcon />
                </IconButton>
              </InputAdornment>
            ),
          }}
        />
      </Paper>
    </Box>
  );
};

export default MessageInput;
