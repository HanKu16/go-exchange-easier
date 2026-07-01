import { Button, type ButtonProps  } from "@mui/material";
import ReportIcon from "@mui/icons-material/Report";
import BookmarkIcon from "@mui/icons-material/Bookmark";

const baseStyles = { borderRadius: "12px", fontWeight: 700 };

export const BaseActionButton = (props: ButtonProps) => (
  <Button 
    {...props}
    disableElevation 
    sx={{ ...baseStyles, ...props.sx }}
  />
);

export const FollowButton = ({ 
  isFollowed, 
  ...props 
}: ButtonProps & { isFollowed: boolean }) => (
  <BaseActionButton 
    {...props} 
    variant="contained"
        endIcon={isFollowed ? <BookmarkIcon /> : <BookmarkIcon />}
  >
    {isFollowed ? "UNSAVE" : "SAVE"}
  </BaseActionButton>
);

export const ChatButton = (props: ButtonProps) => (
  <BaseActionButton 
    {...props} 
    variant="contained"
    sx={{ backgroundColor: "#04315f", "&:hover": { backgroundColor: "#064080" }, ...props.sx }}
  >
    CHAT
  </BaseActionButton>
);

export const ReportButton = (props: ButtonProps) => (
  <BaseActionButton 
    {...props} 
    variant="contained" 
    color="inherit" 
    endIcon={<ReportIcon />}
  >
    REPORT
  </BaseActionButton>
);

