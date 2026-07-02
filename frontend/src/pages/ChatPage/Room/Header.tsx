import basicAvatar from "../../../assets/basic-avatar.png";
import type { HeaderProps } from "../types";
import { useNavigate } from "react-router-dom";
import { ArrowBack as ArrowBackIcon } from "@mui/icons-material";
import {
  Card,
  CardContent,
  Box,
  Avatar,
  Typography,
  IconButton,
  useTheme,
  useMediaQuery,
} from "@mui/material";
import { MiniatureReportButton } from "../../../components/Buttons";
import { useState } from "react";
import { ReportDialog } from "../../../components/ReportDialog";
import { useSnackbar } from "../../../context/SnackBarContext";
import { sendCreateChatReportRequest } from "../../../utils/api/chat-report";

const Header = (props: HeaderProps) => {
  const navigate = useNavigate();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("md"));
  const [isReportDialogOpen, setIsReportDialogOpen] = useState(false);
  const { showAlert } = useSnackbar();
  const maxReportDescriptionLegth = 1000;

  const handleReportConfirm = async (reason: string, description: string | null): Promise<boolean> => {
      const result = await sendCreateChatReportRequest({
        reason: reason,
        description: description,
        reportedUserId: props.targetUserId,
        roomId: props.id,
      });
  
      if (result.isSuccess) {
        showAlert("Chat reported successfully.", "success");
        return true; 
      }
  
      const isSizeError = result.error.fieldErrors?.some(
        (fieldError) => fieldError.code === "SIZE"
      );
      
      const errorMessage = isSizeError
        ? `Report description can not be longer than ${maxReportDescriptionLegth} characters.`
        : result.error.status === "SERVICE_UNAVAILABLE"
          ? "Could not submit the report. The service is unavailable."
          : "Failed to submit the report. Please try again later.";
          
      showAlert(errorMessage, "error");
      return false;
    };

  return (
    <Card
      square
      sx={{
        width: "100%",
        height: "5rem",
        flexShrink: 0,
      }}
    >
      <CardContent
        sx={{
          padding: 2,
          display: "flex",
          flexDirection: "row",
          alignItems: "center",
          height: "100%",
          "&:last-child": { pb: 2 },
        }}
      >
        {isMobile && (
          <IconButton
            onClick={() => navigate("/chat")}
            sx={{ mr: 1 }}
            aria-label="go back"
          >
            <ArrowBackIcon />
          </IconButton>
        )}

        <Box>
          <Avatar
            alt="User avatar"
            src={props.avatarUrl || basicAvatar}
            sx={{ width: 45, height: 45 }}
          />
        </Box>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            marginLeft: 2,
            flexGrow: 1,
            overflow: "hidden",
          }}
        >
          <Typography
            onClick={() => navigate(props.link)}
            variant="subtitle1"
            fontWeight="bold"
            noWrap
            sx={{
              cursor: "pointer",
              display: "inline-block",
              transition: "color 0.2s, text-decoration 0.2s",
              color: "#182c44",
              "&:hover": {
                color: "#244164",
                textDecoration: "underline",
              },
            }}
          >
            {props.name}
          </Typography>
        </Box>
        <Box sx={{ ml: 2, display: "flex", alignItems: "center", flexShrink: 0 }}>
          <MiniatureReportButton setIsReportDialogOpen={setIsReportDialogOpen} />
        </Box>
      <ReportDialog 
        open={isReportDialogOpen}
        onClose={() => setIsReportDialogOpen(false)}
        onConfirm={handleReportConfirm}
        title="Report chat"
        descriptionText="Leave a short description if you want to add context."
        maxReportDescriptionSize={maxReportDescriptionLegth}
      />
      </CardContent>
    </Card>
  );
};

export default Header;
