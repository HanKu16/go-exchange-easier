import { Box } from "@mui/material";
import { useParams } from "react-router-dom";
import type { SimplePage } from "../../../dtos/common/SimplePage";
import { useQuery, type InfiniteData } from "@tanstack/react-query";
import Header from "./Header";
import { useQueryClient } from "@tanstack/react-query";
import { sendGetRoomRequest } from "../../../utils/api/room";
import MessageInput from "./MessageInput";
import type { RoomPreview } from "../../../dtos/room/RoomPreview";
import { cacheKeys } from "../types";
import MessagesContainer from "./MessagesContainer";

const Room = () => {
  const { roomId } = useParams();
  const queryClient = useQueryClient();

  const cachedRooms = queryClient.getQueryData<
    InfiniteData<SimplePage<RoomPreview>>
  >(cacheKeys.allRooms);

  const roomInCache =
    cachedRooms?.pages
      .flatMap((page) => page.content)
      .find((r) => r.id === roomId) ||
    queryClient.getQueryData<RoomPreview>([cacheKeys.newRoom, roomId]);

  const { data: room } = useQuery({
    queryKey: cacheKeys.room(roomId!),
    queryFn: async () => {
      const result = await sendGetRoomRequest(roomId!);
      if (result.isSuccess) {
        return result.data;
      }
      throw new Error("Room was not found.");
    },
    enabled: !!roomId && !roomInCache,
    initialData: roomInCache,
    staleTime: 1000 * 60 * 5,
  });

  if (!room) {
    return <></>;
  }
  return (
    <Box
      sx={{
        height: "100dvh",
        display: "flex",
        flexDirection: "column",
        minHeight: 0,
        alignItems: "center",
        overflow: "hidden",
      }}
    >
      <Header
        id={room.id}
        name={room.name}
        avatarUrl={room.imageUrl}
        link={`/users/${room.targetUserId}`}
      />
      <MessagesContainer />
      <MessageInput />
    </Box>
  );
};

export default Room;
