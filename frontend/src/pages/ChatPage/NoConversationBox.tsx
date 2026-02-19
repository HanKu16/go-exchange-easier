import { Box, Card, Typography, Button, alpha } from "@mui/material";
import ForumTwoToneIcon from "@mui/icons-material/ForumTwoTone";
import AddCommentIcon from "@mui/icons-material/AddComment";
import { useNavigate } from "react-router-dom";

const NoConversationsBox = () => {
  const navigate = useNavigate();

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        minHeight: "300px",
        width: "100%",
        p: 3,
      }}
    >
      <Card
        sx={{
          width: "100%",
          maxWidth: 400,
          p: 4,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          borderRadius: 4,
          boxShadow: "0 8px 24px rgba(0,0,0,0.05)",
          background: "linear-gradient(145deg, #ffffff 0%, #f9f9f9 100%)",
          border: "1px solid #ececec",
        }}
      >
        <Box
          sx={{
            width: 80,
            height: 80,
            borderRadius: "50%",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            bgcolor: (theme) => alpha(theme.palette.primary.main, 0.1),
            color: "primary.main",
            mb: 2,
          }}
        >
          <ForumTwoToneIcon sx={{ fontSize: 40 }} />
        </Box>
        <Typography
          variant="h6"
          sx={{ fontWeight: 700, color: "#2c3e50", mb: 1 }}
        >
          No messages yet
        </Typography>
        <Typography
          variant="body2"
          sx={{
            textAlign: "center",
            color: "#7f8c8d",
            mb: 3,
            lineHeight: 1.6,
          }}
        >
          Your conversation list is empty. Reach out other people and start a
          new chat!
        </Typography>

        <Button
          variant="contained"
          startIcon={<AddCommentIcon />}
          sx={{
            borderRadius: 8,
            px: 4,
            textTransform: "none",
            boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
          }}
          onClick={() => navigate("/follows")}
        >
          Go to your follows
        </Button>
      </Card>
    </Box>
  );
};

export default NoConversationsBox;
