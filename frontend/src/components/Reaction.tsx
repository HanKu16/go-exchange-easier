import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import ThumbUp from "@mui/icons-material/ThumbUp";
import ThumbDown from "@mui/icons-material/ThumbDown";
import type { ReactionDetails } from "../dtos/reaction/ReactionDetails";
import type { ReactionType } from "../types/ReactionType";

export type ReactionProps = ReactionDetails & {
  handleReactionChange: (newReaction: ReactionType) => void;
};

const Reaction = (props: ReactionProps) => {
  const defaultColor = "";
  const reactionColor: string = props.isSet ? "#1e3756ff" : defaultColor;

  if (props.type === "Like") {
    return (
      <IconButton
        aria-label="like review"
        onClick={() => props.handleReactionChange(props.type)}
      >
        <ThumbUp sx={{ marginRight: 0.5, color: reactionColor }} />
        <Typography sx={{ fontSize: "0.9rem", color: reactionColor }}>
          {props.count}
        </Typography>
      </IconButton>
    );
  } else if (props.type === "Dislike") {
    return (
      <IconButton
        aria-label="dislike review"
        onClick={() => props.handleReactionChange(props.type)}
      >
        <ThumbDown sx={{ marginRight: 0.5, color: reactionColor }} />
        <Typography sx={{ fontSize: "0.9rem", color: reactionColor }}>
          {props.count}
        </Typography>
      </IconButton>
    );
  }
};

export default Reaction;
