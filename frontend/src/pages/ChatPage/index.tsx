import { Box, useMediaQuery, useTheme } from "@mui/material";
import Navbar from "../../components/Navbar";
import RoomList from "./RoomList";
import Room from "./Room";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { sendGetOrCreateRoomRequest } from "../../utils/api/room";
import { useEffect } from "react";
import { useQueryClient, useMutation } from "@tanstack/react-query";

const ChatPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const targetUserId = location.state?.targetUserId;
  const queryClient = useQueryClient();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("md"));
  const { roomId } = useParams();

  const { mutate } = useMutation({
    mutationFn: () => sendGetOrCreateRoomRequest({ targetUserId }),
    onSuccess: (result) => {
      if (result.isSuccess) {
        const newRoomId = result.data.id;
        queryClient.setQueryData(["new-room", newRoomId], result.data);
        navigate(`/chat/${newRoomId}`, { replace: true });
      }
    },
  });

  useEffect(() => {
    if (targetUserId) {
      mutate();
    }
  }, [targetUserId]);

  const showRoomList = (): boolean => {
    if (!isMobile) {
      return true;
    }
    return roomId === undefined || roomId === null;
  };

  return (
    <Box
      sx={{
        display: "flex",
        height: "100dvh",
        flexDirection: "column",
        overflow: "hidden",
        minHeight: 0,
      }}
    >
      <Navbar />
      <Box
        sx={{
          display: "flex",
          flexGrow: 1,
          overflow: "hidden",
          flexDirection: { xs: "column", md: "row" },
        }}
      >
        {showRoomList() && <RoomList />}
        {isMobile ? (
          <Room />
        ) : (
          <Box
            sx={{
              flexGrow: 1,
              minWidth: 0,
              display: "flex",
              flexDirection: "column",
            }}
          >
            <Room />
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default ChatPage;
