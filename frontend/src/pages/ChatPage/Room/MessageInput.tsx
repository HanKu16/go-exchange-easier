import React, { useEffect, useState } from "react";
import {
  TextField,
  IconButton,
  InputAdornment,
  Paper,
  Popover,
  Box,
  Tooltip,
} from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import EmojiEmotionsIcon from "@mui/icons-material/EmojiEmotions";
import { useParams } from "react-router-dom";
import useChatSync from "../hooks/useChatSync";
import useSendMessage from "../hooks/useSendMessage";
import type { MessageInputProps } from "../types";
import { useSnackbar } from "../../../context/SnackBarContext";

const MessageInput = (props: MessageInputProps) => {
  const [message, setMessage] = useState("");
  const { roomId } = useParams();
  const { syncAll } = useChatSync(roomId!);
  const { sendMessage, isError, error } = useSendMessage(roomId!, syncAll);
  const { showAlert } = useSnackbar();

  const maxMessageSize = 1000;

  const handleSend = () => {
    if (message.trim() && message.length <= maxMessageSize) {
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

  const [emojiAnchor, setEmojiAnchor] = useState<HTMLElement | null>(null);
  const emojis = [
    "😀",
    "😃",
    "😂",
    "😍",
    "😊",
    "👍",
    "🙏",
    "🎉",
    "🔥",
    "❤️",
    "😢",
    "😮",
    "😅",
    "🤔",
    "🙌",
  ];

  const openEmojiPicker = (e: React.MouseEvent<HTMLElement>) => {
    setEmojiAnchor(e.currentTarget);
  };

  const closeEmojiPicker = () => setEmojiAnchor(null);

  const handleEmojiClick = (emoji: string) => {
    setMessage((prev) => prev + emoji);
    setEmojiAnchor(null);
  };

  useEffect(() => {
    if (isError) {
      const apiError = error as any;
      if (apiError?.fieldErrors && apiError.fieldErrors.some((e: any) => e.code === "SIZE")) {
        showAlert(`Message is too long (max ${maxMessageSize} characters).`, "error");
      } else {
        showAlert("Failed to send a message.", "error");
      }
    }
  }, [isError, error, maxMessageSize]);

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
          inputProps={{ maxLength: maxMessageSize }}
          onKeyDown={handleKeyPress}
          disabled={props.disabled}
          sx={{
            backgroundColor: "white",
            borderRadius: 1,
            "& .MuiOutlinedInput-root": {
              "& fieldset": { borderColor: "#182c44" },
              "&:hover fieldset": { borderColor: "#182c44" },
              "&.Mui-focused fieldset": { borderColor: "#182c44" },
            },
          }}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  onClick={openEmojiPicker}
                  disabled={props.disabled}
                  sx={{ color: "#182c44", mr: 1 }}
                  aria-label="emoji picker"
                >
                  <EmojiEmotionsIcon />
                </IconButton>
                <Popover
                  open={Boolean(emojiAnchor)}
                  anchorEl={emojiAnchor}
                  onClose={closeEmojiPicker}
                  anchorOrigin={{ vertical: "top", horizontal: "right" }}
                  transformOrigin={{ vertical: "bottom", horizontal: "right" }}
                >
                  <Box sx={{ p: 1 }}>
                    <Box
                      sx={{
                        display: "grid",
                        gridTemplateColumns: "repeat(4, 1fr)",
                        gap: 1,
                        width: 200,
                      }}
                    >
                      {emojis.map((e) => (
                        <Box key={e} sx={{ display: "flex", justifyContent: "center" }}>
                          <Tooltip title={e}>
                            <IconButton onClick={() => handleEmojiClick(e)} size="small">
                              <span style={{ fontSize: 18 }}>{e}</span>
                            </IconButton>
                          </Tooltip>
                        </Box>
                      ))}
                    </Box>
                  </Box>
                </Popover>
                <IconButton
                  onClick={handleSend}
                  disabled={!message.trim() || props.disabled}
                  sx={{
                    color: "#182c44",
                    transition: "all 0.2s ease",

                    "&:hover": {
                      backgroundColor: "rgba(24, 44, 68, 0.08)",
                    },
                    "&:active": {
                      color: "#244164 !important",
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
        <Box sx={{ display: "flex", justifyContent: "flex-end", mt: 0.5, mx: 1 }}>
          <small style={{ color: message.length > maxMessageSize ? "#d32f2f" : "#616161" }}>
            {message.length}/{maxMessageSize}
          </small>
        </Box>
      </Paper>
    </Box>
  );
};

export default MessageInput;
