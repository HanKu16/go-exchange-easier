import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Avatar from "@mui/material/Avatar";
import IconButton, { type IconButtonProps } from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { Rating } from "@mui/material";
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
import React from "react";
import { Tooltip } from "@mui/material";
import { useConfirmation } from "../context/ConfirmationDialogContext";
import { useNavigate } from "react-router-dom";

const reactionOrder = {
  Like: 1,
  Dislike: 2,
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
  showDeleteButton: boolean;
  route?: string;
  removeFromPage?: () => void;
};

const UniversityReview = (props: UniversityReviewProps) => {
  const [canChangeReaction, setCanChangeReaction] = useState<boolean>(true);
  const { showAlert } = useSnackbar();
  const { showConfirmation } = useConfirmation();
  const navigate = useNavigate();

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
          <>
            <IconButton aria-label="add to favorites">
              <Rating name="read-only" value={props.starRating} readOnly />
            </IconButton>
            {props.showDeleteButton && (
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
          </>
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
            <Reaction {...r} handleReactionChange={handleReactionChange} />
          ))}
      </CardActions>
    </Card>
  );
};

export default UniversityReview;
