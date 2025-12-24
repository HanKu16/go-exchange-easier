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
import { sendDeleteUniversityReviewReactionRequest } from "../utils/review";
import { sendAddUniversityReviewReactionRequest } from "../utils/review";
import type { ReactionDetails } from "../types/ReactionDetails";
import Reaction from "./Reaction";
import { useSnackbar } from "../context/SnackBarContext";

export type UniversityReviewProps = {
  id: number;
  title: string;
  subheader: string;
  starRating: number;
  textContent: string;
  reactions: ReactionDetails[];
};

const UniversityReview = (props: UniversityReviewProps) => {
  const [canChangeReaction, setCanChangeReaction] = useState<boolean>(true);
  const { showAlert } = useSnackbar();

  const handleReactionChange = async (activeReactionId: number) => {
    if (!canChangeReaction) {
      showAlert("You have to wait to update reaction again.", "warning");
      return;
    }
    setCanChangeReaction(false);
    const newReactionProps = reactionsProps.map((rp) => ({ ...rp }));
    for (let i = 0; i < newReactionProps.length; ++i) {
      const wasSetBefore = newReactionProps[i].isSet;
      if (newReactionProps[i].typeId === activeReactionId) {
        if (newReactionProps[i].isSet) {
          newReactionProps[i].isSet = false;
          newReactionProps[i].count = newReactionProps[i].count - 1;
          await sendDeleteUniversityReviewReactionRequest(props.id, {
            reactionTypeId: newReactionProps[i].typeId,
          });
        } else {
          newReactionProps[i].isSet = true;
          newReactionProps[i].count = newReactionProps[i].count + 1;
          await sendAddUniversityReviewReactionRequest(props.id, {
            reactionTypeId: newReactionProps[i].typeId,
          });
        }
      }
      if (wasSetBefore && newReactionProps[i].typeId !== activeReactionId) {
        newReactionProps[i].isSet = false;
        newReactionProps[i].count = newReactionProps[i].count - 1;
      }
    }
    setReactionsProps(newReactionProps);
    setCanChangeReaction(true);
  };

  const [reactionsProps, setReactionsProps] = useState<ReactionDetails[]>(
    props.reactions.map((r) => ({
      typeId: r.typeId,
      name: r.name,
      isSet: r.isSet,
      count: r.count,
    }))
  );

  return (
    <Card sx={{ width: "95%" }}>
      <CardHeader
        avatar={<Avatar aria-label="User avatar" src={basicAvatar} />}
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
          .sort((a, b) => a.typeId - b.typeId)
          .map((r) => (
            <Reaction {...r} handleReactionChange={handleReactionChange} />
          ))}
      </CardActions>
    </Card>
  );
};

export default UniversityReview;
