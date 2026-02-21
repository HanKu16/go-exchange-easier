import basicAvatar from "../../assets/basic-avatar.png";
import type { RoomHeaderProps } from "./types";
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

const RoomHeader = (props: RoomHeaderProps) => {
  const navigate = useNavigate();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("md"));

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
          <Typography variant="subtitle1" fontWeight="bold" noWrap>
            {props.name}
          </Typography>
        </Box>
      </CardContent>
    </Card>
  );
};

export default RoomHeader;
