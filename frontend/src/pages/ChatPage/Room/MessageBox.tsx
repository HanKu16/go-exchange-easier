import {
  Avatar,
  Box,
  CircularProgress,
  IconButton,
  Typography,
} from "@mui/material";
import type { MessageBoxProps } from "../types";
import basicAvatar from "../../../assets/basic-avatar.png";
import dayjs from "dayjs";
import DeleteIcon from "@mui/icons-material/Delete";
import { useEffect, useState } from "react";
import { useConfirmation } from "../../../context/ConfirmationDialogContext";

const MessageBox = (props: MessageBoxProps) => {
  const [isWaitingForBeingDeleted, setIsWaitingForBeingDeleted] =
    useState<boolean>(false);
  const [wasDeletionFailure, setWasDeletionFailure] = useState<boolean>(false);
  const [isActive, setIsActive] = useState<boolean>(false);
  const { showConfirmation } = useConfirmation();

  const handleTap = () => {
    setIsActive(!isActive);
  };

  const handleDeletion = async () => {
    setIsWaitingForBeingDeleted(true);
    const wasDeleted: boolean = await props.onDelete();
    if (!wasDeleted) {
      setWasDeletionFailure(true);
      setIsWaitingForBeingDeleted(false);
    }
  };

  useEffect(() => {
    if (isActive) {
      const timer = setTimeout(() => setIsActive(false), 5000);
      return () => clearTimeout(timer);
    }
  }, [isActive]);

  const getDeletionInteractionElement = () => {
    return (
      <Box
        sx={{
          width: 32,
          height: 32,
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          flexShrink: 0,
          mx: 0.5,
        }}
      >
        {!isWaitingForBeingDeleted ? (
          <IconButton
            className="delete-button"
            size="small"
            sx={{
              opacity: 0,
              transition: "opacity 0.2s",
              color: "error.light",
              "&:hover": { color: "error.main" },
            }}
            onClick={() => {
              showConfirmation({
                title: "Are you sure you want to delete this message?",
                message: "This action cannot be undone.",
                onConfirm: handleDeletion,
                confirmColor: "error",
              });
            }}
          >
            <DeleteIcon sx={{ fontSize: 16 }} />
          </IconButton>
        ) : (
          <CircularProgress size={16} sx={{ color: "#1f3958ff" }} />
        )}
      </Box>
    );
  };

  return (
    <Box
      onClick={handleTap}
      sx={{
        marginY: 1,
        width: "100%",
        flexDirection: "row",
        display: "flex",
        justifyContent: props.isUserMessage ? "flex-end" : "flex-start",
        "&:hover .delete-button": { opacity: 1 },
        "& .delete-button": {
          opacity: isActive ? 1 : 0,
        },
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
              display: "flex",
              alignItems: "center",
              flexDirection: props.isUserMessage ? "row-reverse" : "row",
            }}
          >
            <Box
              sx={{
                backgroundColor: props.isUserMessage ? "#f4f2f2" : "#e4e0e0",
                borderRadius: 2,
                padding: 0.75,
                userSelect: "none",
                WebkitUserSelect: "none",
              }}
            >
              {props.textContent}
            </Box>
            {props.isUserMessage && getDeletionInteractionElement()}
          </Box>
          {props.isPending && (
            <CircularProgress
              size={12}
              sx={{ color: "#1f3958ff", marginTop: 0.5 }}
            />
          )}
          {wasDeletionFailure && (
            <Typography
              variant="caption"
              sx={{
                color: "error.main", // Standardowy czerwony z MUI
                fontSize: "0.7rem", // Bardzo mały rozmiar, żeby nie dominował
                fontWeight: "bold",
                marginTop: 0.25,
                paddingX: 1,
                animation: "fadeIn 0.3s ease-in-out",
              }}
            >
              Failed to delete message
            </Typography>
          )}
        </Box>
      </Box>
    </Box>
  );
};

export default MessageBox;
