import { Box, Container } from "@mui/material";
import { useRef } from "react";
import { useParams } from "react-router-dom";
import type { SimplePage } from "../../dtos/common/SimplePage";
import type { MessageDetails } from "../../dtos/message/MessageDetails";
import { sendGetMessagePageRequest } from "../../utils/api/message";
import { useQuery } from "@tanstack/react-query";
import RoomHeader from "./RoomHeader";
import { useQueryClient } from "@tanstack/react-query";
import type { RoomSummary } from "../../dtos/room/RoomSummary";
import type { RoomDetails } from "../../dtos/room/RoomDetails";
import MessageBox from "./MessageBox";
import { useSignedInUser } from "../../context/SignedInUserContext";
import { sendGetRoomRequest } from "../../utils/api/room";
import MessageInput from "./MessageInput";

const Room = () => {
  const { roomId } = useParams();
  const lastFetchedPage = useRef<number>(0);
  const pageSize = 30;
  const queryClient = useQueryClient();

  interface InfiniteDataStructure {
    pages: SimplePage<RoomSummary>[];
    pageParams: number[];
  }

  const cachedData = queryClient.getQueryData<InfiniteDataStructure>(["rooms"]);
  const { signedInUser } = useSignedInUser();

  const roomInCache =
    cachedData?.pages
      .flatMap((page) => page.content)
      .find((r) => r.id === roomId) ||
    queryClient.getQueryData<RoomDetails>(["new-room", roomId]);

  const { data: room } = useQuery({
    queryKey: ["room", roomId],
    queryFn: async () => {
      const result = await sendGetRoomRequest(roomId!);
      if (result.isSuccess) return result.data;
      throw new Error("Room not found");
    },
    enabled: !!roomId && !roomInCache,
    initialData: roomInCache,
    staleTime: 1000 * 60 * 5,
  });

  const { data, isLoading, isError } = useQuery({
    queryKey: ["messages", roomId, lastFetchedPage.current],
    queryFn: async (): Promise<SimplePage<MessageDetails>> => {
      const result = await sendGetMessagePageRequest(
        roomId!,
        lastFetchedPage.current,
        pageSize,
      );

      if (result.isSuccess) {
        return result.data;
      } else {
        throw new Error("Failed to load messages.");
      }
    },
    enabled: !!roomId,
  });
  if (roomId === undefined) {
    return <></>;
  }

  if (isLoading) return <Box>Loading...</Box>;
  if (isError) return <Box>Fetch error...</Box>;

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
      <RoomHeader
        id={room.id}
        name={room.name}
        avatarUrl={room.imageUrl}
        link={"NOT IMPLEMENTED YET"}
      />
      <Container
        sx={{
          flexGrow: 1,
          width: "100%",
          display: "flex",
          flexDirection: "column-reverse",
          backgroundColor: "#dedede",
          alignItems: "center",
          boxShadow: 3,
          bgcolor: "background.paper",
          overflowY: "auto",
          paddingBottom: { xs: 1, md: 2 },
        }}
      >
        <Box sx={{ flexGrow: 1 }} />
        {data?.content.map((m) => (
          <MessageBox
            id={m.id}
            textContent={m.textContent}
            nick={m.author.nick}
            avatarUrl={m.author.avatarUrl}
            dateAndTime={m.createdAt}
            isUserMessage={m.author.id === signedInUser.id}
            key={m.id}
          />
        ))}
      </Container>
      <Box
        sx={{
          flexShrink: 0,
          width: "100%",
          backgroundColor: "#f5f5f5",
          borderTop: "1px solid #ccc",
          p: 1,
        }}
      >
        <MessageInput onSendMessage={(text) => console.log(text)} />
      </Box>
    </Box>
  );
};

export default Room;
