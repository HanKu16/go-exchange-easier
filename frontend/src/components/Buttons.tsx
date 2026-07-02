import { Button, IconButton, Tooltip, type ButtonProps  } from "@mui/material";
import ReportIcon from "@mui/icons-material/Report";
import BookmarkIcon from "@mui/icons-material/Bookmark";
import SendIcon from "@mui/icons-material/Send";

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
    endIcon={<SendIcon />}
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

export type MiniatureButtonProps = ButtonProps & {
  setIsReportDialogOpen: (isOpen: boolean) => void;
}

export const MiniatureReportButton = (props: MiniatureButtonProps) => (
     <Tooltip title="Report review" arrow>
        <IconButton
          aria-label="report review"
          size="small"
          onClick={() => props.setIsReportDialogOpen(true)}
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
          <ReportIcon sx={{ fontSize: 18 }} />
        </IconButton>
     </Tooltip>
)

