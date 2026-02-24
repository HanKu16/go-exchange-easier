import { Box, Container } from "@mui/material";
import { useParams } from "react-router-dom";
import type { SimplePage } from "../../dtos/common/SimplePage";
import type { MessageDetails } from "../../dtos/message/MessageDetails";
import {
  sendCreateMessageRequest,
  sendGetMessagePageRequest,
} from "../../utils/api/message";
import {
  useInfiniteQuery,
  useQuery,
  type InfiniteData,
} from "@tanstack/react-query";
import RoomHeader from "./RoomHeader";
import { useQueryClient } from "@tanstack/react-query";
import type { RoomSummary } from "../../dtos/room/RoomSummary";
import MessageBox from "./MessageBox";
import { useSignedInUser } from "../../context/SignedInUserContext";
import { sendGetRoomRequest } from "../../utils/api/room";
import MessageInput from "./MessageInput";
import { useSnackbar } from "../../context/SnackBarContext";
import LoadingMessages from "./LoadingMessages";
import LoadingChatHistoryError from "./LoadingChatHistoryError";
import { useState } from "react";
import type { TemporaryMessage } from "./types";
import TemporaryMessageBox from "./TemporaryMessageBox";

const Room = () => {
  const { roomId } = useParams();
  const pageSize = 30;
  const queryClient = useQueryClient();
  const { showAlert } = useSnackbar();
  const [pendingMessages, setPendingMessages] = useState<TemporaryMessage[]>(
    [],
  );

  const cachedRooms = queryClient.getQueryData<
    InfiniteData<SimplePage<RoomSummary>>
  >(["rooms"]);
  const { signedInUser } = useSignedInUser();

  const roomInCache =
    cachedRooms?.pages
      .flatMap((page) => page.content)
      .find((r) => r.id === roomId) ||
    queryClient.getQueryData<RoomSummary>(["new-room", roomId]);

  const { data: room } = useQuery({
    queryKey: ["room", roomId],
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

  const handleScroll = (e: React.UIEvent<HTMLDivElement>) => {
    const { scrollTop, scrollHeight, clientHeight } = e.currentTarget;
    const threshold = 300;
    const isNearTop =
      Math.abs(scrollTop) + clientHeight >= scrollHeight - threshold;
    if (isNearTop && hasNextPage && !isFetchingNextPage && !isError) {
      fetchNextPage();
    }
  };

  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isLoading,
    isError,
  } = useInfiniteQuery({
    queryKey: ["messages", roomId],
    queryFn: async ({ pageParam = 0 }) => {
      await new Promise((f) => setTimeout(f, 3000));

      const result = await sendGetMessagePageRequest(
        roomId!,
        pageParam,
        pageSize,
      );
      if (!result.isSuccess) {
        showAlert("Failed to load chat history.", "error");
        throw new Error("Failed to load chat history.");
      }
      return result.data;
    },
    initialPageParam: 0,
    getNextPageParam: (lastPage) => {
      const nextPage = lastPage.pageNumber + 1;
      return nextPage < lastPage.totalPages ? nextPage : undefined;
    },
    enabled: roomId != undefined,
    retry: 4,
    retryDelay: (attemptIndex) => Math.min(1000 * 2 ** attemptIndex, 30000),
  });

  const messages: MessageDetails[] =
    data?.pages.flatMap((page) =>
      page.content.map((m) => ({
        id: m.id,
        createdAt: m.createdAt,
        textContent: m.textContent,
        author: {
          id: m.author.id,
          nick: m.author.nick,
          avatarUrl: m.author.avatarUrl,
        },
      })),
    ) ?? [];

  const updateMessageCache = (roomId: string, newMessage: MessageDetails) => {
    queryClient.setQueryData<InfiniteData<SimplePage<MessageDetails>>>(
      ["messages", roomId],
      (oldData) => {
        if (!oldData) {
          return oldData;
        }
        return {
          ...oldData,
          pages: oldData.pages.map((page, index) =>
            index === 0
              ? { ...page, content: [newMessage, ...page.content] }
              : page,
          ),
        };
      },
    );
  };

  const sendMessage = async (messageText: string) => {
    if (!roomId) {
      return;
    }
    const result = await sendCreateMessageRequest(roomId, {
      textContent: messageText,
    });
    const tempId = crypto.randomUUID();
    const tempMessage: TemporaryMessage = {
      tempId: tempId,
      createdAt: new Date().toISOString(),
      textContent: messageText,
      isSuccessfullySend: undefined,
      roomId: roomId,
    };
    setPendingMessages((prev) => [tempMessage, ...prev]);
    await new Promise((f) => setTimeout(f, 1500));
    if (result.isSuccess) {
      const newPendingMessages = pendingMessages.filter(
        (m: TemporaryMessage) => m.tempId !== tempId,
      );
      setPendingMessages(newPendingMessages);
      updateMessageCache(roomId, result.data);
    } else {
      setPendingMessages((prev) =>
        prev.map((m) =>
          m.tempId === tempId ? { ...m, isSuccessfullySend: false } : m,
        ),
      );
    }
  };

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
        onScroll={handleScroll}
      >
        {pendingMessages.map((m) =>
          m.roomId === roomId ? <TemporaryMessageBox message={m} /> : <></>,
        )}
        {messages.map((m) => (
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
        {(isLoading || isFetchingNextPage) && <LoadingMessages />}
        {isError && !isLoading && <LoadingChatHistoryError />}
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
        <MessageInput onSendMessage={sendMessage} />
      </Box>
    </Box>
  );
};

export default Room;
