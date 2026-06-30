import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Avatar from "@mui/material/Avatar";
import IconButton, { type IconButtonProps } from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import {
  Box,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Rating,
  TextField,
  Tooltip,
} from "@mui/material";
import basicAvatar from "../assets/basic-avatar.png";
import { useState } from "react";
import {
  sendDeleteReviewRequest,
  sendDeleteUniversityReviewReactionRequest,
} from "../utils/api/university-review";
import { sendAddUniversityReviewReactionRequest } from "../utils/api/university-review";
import type { ReactionDetails } from "../dtos/reaction/ReactionDetails";
import Reaction from "./Reaction";
import { useSnackbar } from "../context/SnackBarContext";
import type { ReactionType } from "../types/ReactionType";
import ClearIcon from "@mui/icons-material/Clear";
import FlagOutlinedIcon from "@mui/icons-material/FlagOutlined";
import React from "react";
import { useConfirmation } from "../context/ConfirmationDialogContext";
import { useNavigate } from "react-router-dom";
import { sendCreateUniversityReviewReportRequest } from "../utils/api/university-review-report";
import { useSignedInUser } from "../context/SignedInUserContext";

const reactionOrder = {
  LIKE: 1,
  DISLIKE: 2,
};

type DeleteCircleButtonProps = IconButtonProps & {
  onDelete: () => void;
  tooltipText?: string;
};

const DeleteCircleButton: React.FC<DeleteCircleButtonProps> = ({
  onDelete,
  tooltipText = "Delete",
  ...props
}) => {
  return (
    <Tooltip title={tooltipText} arrow>
      <IconButton
        onClick={onDelete}
        size="small"
        {...props}
        sx={{
          bgcolor: "rgba(0, 0, 0, 0.04)",
          color: "text.secondary",
          transition: "all 0.2s ease-in-out",
          "&:hover": {
            bgcolor: "error.lighter",
            color: "error.main",
            backgroundColor: "#fee2e2",
            transform: "scale(1.1)",
          },
          ...props.sx,
        }}
      >
        <ClearIcon sx={{ fontSize: 18 }} />
      </IconButton>
    </Tooltip>
  );
};

export type UniversityReviewProps = {
  id: number;
  title: string;
  avatarUrl?: string;
  subheader: string;
  starRating: number;
  textContent: string;
  reactions: ReactionDetails[];
  authorId: number;
  route?: string;
  removeFromPage?: () => void;
};

const UniversityReview = (props: UniversityReviewProps) => {
  const [canChangeReaction, setCanChangeReaction] = useState<boolean>(true);
  const [isReportDialogOpen, setIsReportDialogOpen] = useState(false);
  const [reportDescription, setReportDescription] = useState("");
  const [isReporting, setIsReporting] = useState(false);
  const [reportErrorMessage, setReportErrorMessage] = useState<string | null>(
    null,
  );
  const maxReportDescriptionSize = 1000;
  const { showAlert } = useSnackbar();
  const { showConfirmation } = useConfirmation();
  const navigate = useNavigate();
  const { signedInUser } = useSignedInUser();

  const handleReactionChange = async (activeReaction: ReactionType) => {
    if (!canChangeReaction) {
      showAlert("You have to wait to update reaction again.", "warning");
      return;
    }
    setCanChangeReaction(false);
    const newReactionProps = reactionsProps.map((rp) => ({ ...rp }));
    for (let i = 0; i < newReactionProps.length; ++i) {
      const wasSetBefore = newReactionProps[i].isSet;
      if (newReactionProps[i].type === activeReaction) {
        if (newReactionProps[i].isSet) {
          newReactionProps[i].isSet = false;
          newReactionProps[i].count = newReactionProps[i].count - 1;
          await sendDeleteUniversityReviewReactionRequest(props.id);
        } else {
          newReactionProps[i].isSet = true;
          newReactionProps[i].count = newReactionProps[i].count + 1;
          await sendAddUniversityReviewReactionRequest(props.id, {
            reactionType: newReactionProps[i].type,
          });
        }
      }
      if (wasSetBefore && newReactionProps[i].type !== activeReaction) {
        newReactionProps[i].isSet = false;
        newReactionProps[i].count = newReactionProps[i].count - 1;
      }
    }
    setReactionsProps(newReactionProps);
    setCanChangeReaction(true);
  };

  const [reactionsProps, setReactionsProps] = useState<ReactionDetails[]>(
    props.reactions.map((r) => ({
      type: r.type,
      isSet: r.isSet,
      count: r.count,
    })),
  );

  const handleDeletion = async () => {
    const result = await sendDeleteReviewRequest(props.id);
    if (result.isSuccess) {
      showAlert("Review deleted successfully.", "success");
      props.removeFromPage?.();
    } else {
      showAlert("Failed to delete the review.", "error");
    }
  };

  const openReportDialog = () => {
    setReportDescription("");
    setReportErrorMessage(null);
    setIsReportDialogOpen(true);
  };

  const closeReportDialog = () => {
    if (isReporting) {
      return;
    }
    setIsReportDialogOpen(false);
    setReportDescription("");
    setReportErrorMessage(null);
  };

  const handleReport = async () => {
    setIsReporting(true);
    setReportErrorMessage(null);

    if (reportDescription.length > maxReportDescriptionSize) {
      const errorMessage = `Report description can not be longer than ${maxReportDescriptionSize} characters.`;
      setReportErrorMessage(errorMessage);
      showAlert(errorMessage, "error");
      setIsReporting(false);
      return;
    }

    const result = await sendCreateUniversityReviewReportRequest(props.id, {
      description:
        reportDescription.trim().length > 0 ? reportDescription.trim() : null,
    });
    if (result.isSuccess) {
      showAlert("Review reported successfully.", "success");
      setIsReportDialogOpen(false);
      setReportDescription("");
    } else {
      const isSizeError = result.error.fieldErrors.some(
        (fieldError) => fieldError.code === "SIZE",
      );
      const errorMessage = isSizeError
        ? `Report description can not be longer than ${maxReportDescriptionSize} characters.`
        : result.error.status === "SERVICE_UNAVAILABLE"
          ? "Could not submit the report. The service is unavailable."
          : "Failed to submit the report. Please try again later.";
      showAlert(errorMessage, "error");
    }

    setIsReporting(false);
  };

  return (
    <Card sx={{ width: "95%" }}>
      <CardHeader
        avatar={
          <Avatar
            aria-label="User avatar"
            src={props.avatarUrl ? props.avatarUrl : basicAvatar}
          >
            <img src={basicAvatar} style={{ width: "100%", height: "100%" }} />
          </Avatar>
        }
        action={
          <Box sx={{ display: "flex", alignItems: "center", gap: 0.5 }}>
 
            <IconButton aria-label="add to favorites">
              <Rating name="read-only" value={props.starRating} readOnly />
            </IconButton>
            {(props.authorId != signedInUser.id ) &&<Tooltip title="Report review" arrow>
              <IconButton
                aria-label="report review"
                size="small"
                onClick={openReportDialog}
                sx={{
                  bgcolor: "rgba(0, 0, 0, 0.04)",
                  color: "text.secondary",
                  transition: "all 0.2s ease-in-out",
                  "&:hover": {
                    bgcolor: "warning.lighter",
                    color: "warning.main",
                    transform: "scale(1.1)",
                  },
                }}
              >
                <FlagOutlinedIcon sx={{ fontSize: 18 }} />
              </IconButton>
            </Tooltip>
            }
            {(props.authorId == signedInUser.id) && (
              <DeleteCircleButton
                onDelete={() => {
                  showConfirmation({
                    title: "Are you sure you want to delete this review?",
                    message: "This action cannot be undone.",
                    onConfirm: handleDeletion,
                    confirmColor: "error",
                  });
                }}
              />
            )}
          </Box>
        }
        title={
          <Typography
            onClick={() => {
              if (props.route) navigate(props.route);
            }}
            variant="body2"
            sx={{
              cursor: props.route ? "pointer" : "default",
              "&:hover": props.route
                ? {
                    textDecoration: "underline",
                  }
                : {},
            }}
          >
            {props.title}
          </Typography>
        }
        subheader={props.subheader}
        sx={{ marginRight: 1 }}
      />
      <CardContent>
        <Typography variant="body2" sx={{ color: "text.secondary" }}>
          {props.textContent}
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        {reactionsProps
          .sort((a, b) => {
            const priorityA = reactionOrder[a.type];
            const priorityB = reactionOrder[b.type];
            return priorityA - priorityB;
          })
          .map((r) => (
            <Reaction
              key={r.type}
              {...r}
              handleReactionChange={handleReactionChange}
            />
          ))}
      </CardActions>
      <Dialog
        open={isReportDialogOpen}
        onClose={closeReportDialog}
        fullWidth
        maxWidth="sm"
        disableScrollLock
        PaperProps={{ sx: { borderRadius: 4 } }}
      >
        <DialogTitle sx={{ fontWeight: 800, color: "#04315f" }}>
          Report this review
        </DialogTitle>
        <DialogContent sx={{ pt: 1 }}>
          <DialogContentText sx={{ color: "#04315f", mb: 2 }}>
            Leave a short description if you want to add context. You can also
            submit the report without any text.
          </DialogContentText>
          <TextField
            autoFocus
            fullWidth
            label="Description"
            placeholder="Optional details about the issue"
            multiline
            minRows={4}
            value={reportDescription}
            disabled={isReporting}
            error={reportDescription.length > maxReportDescriptionSize}
            helperText={
              reportDescription.length > maxReportDescriptionSize
                ? `Maximum length is ${maxReportDescriptionSize} characters.`
                : `${reportDescription.length}/${maxReportDescriptionSize} characters`
            }
            onChange={(event) => {
              setReportDescription(event.target.value);
              if (reportErrorMessage) {
                setReportErrorMessage(null);
              }
            }}
            sx={{
              mt: 0.5,
              "& .MuiOutlinedInput-root": {
                "& fieldset": { borderColor: "#04315f" },
                "&:hover fieldset": { borderColor: "#04315f" },
                "&.Mui-focused fieldset": { borderColor: "#04315f" },
              },
              "& .MuiInputLabel-root": {
                color: "#04315f",
              },
              "& .MuiInputLabel-root.Mui-focused": {
                color: "#04315f",
              },
            }}
          />
        </DialogContent>
        <DialogActions sx={{ p: 3, gap: 1.5 }}>
          <Button
            fullWidth
            onClick={closeReportDialog}
            variant="outlined"
            disabled={isReporting}
            sx={{ borderRadius: "12px", py: 1, borderColor: "#04315f", color: "#04315f", "&:hover": { borderColor: "#04315f", backgroundColor: "#e6f0ff" } }}
          >
            Cancel
          </Button>
          <Button
            fullWidth
            onClick={handleReport}
            variant="contained"
            disableElevation
            disabled={isReporting || reportDescription.length > maxReportDescriptionSize}
            sx={{ 
              borderRadius: "12px", 
              py: 1, 
              fontWeight: 700,
              backgroundColor: "#04315f",
              "&:hover": {
                backgroundColor: "#064080",
              }
            }}
          >
            {isReporting ? (
              <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                <CircularProgress color="inherit" size={18} />
                Reporting...
              </Box>
            ) : (
              "Report"
            )}
          </Button>
        </DialogActions>
      </Dialog>
    </Card>
  );
};

export default UniversityReview;
