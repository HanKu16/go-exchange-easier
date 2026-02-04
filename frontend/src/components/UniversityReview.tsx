import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { Rating } from "@mui/material";
import basicAvatar from "../assets/examples/basic-avatar.png";
import { useState } from "react";
import { sendDeleteUniversityReviewReactionRequest } from "../utils/api/university-review";
import { sendAddUniversityReviewReactionRequest } from "../utils/api/university-review";
import type { ReactionDetails } from "../dtos/reaction/ReactionDetails";
import Reaction from "./Reaction";
import { useSnackbar } from "../context/SnackBarContext";
import type { ReactionType } from "../types/ReactionType";

const reactionOrder = {
  Like: 1,
  Dislike: 2,
};

export type UniversityReviewProps = {
  id: number;
  title: string;
  avatarUrl?: string;
  subheader: string;
  starRating: number;
  textContent: string;
  reactions: ReactionDetails[];
};

const UniversityReview = (props: UniversityReviewProps) => {
  const [canChangeReaction, setCanChangeReaction] = useState<boolean>(true);
  const { showAlert } = useSnackbar();

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

  return (
    <Card sx={{ width: "95%" }}>
      <CardHeader
        avatar={
          <Avatar
            aria-label="User avatar"
            src={props.avatarUrl ? props.avatarUrl : basicAvatar}
          />
        }
        action={
          <IconButton aria-label="add to favorites">
            <Rating name="read-only" value={props.starRating} readOnly />
          </IconButton>
        }
        title={props.title}
        subheader={props.subheader}
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
