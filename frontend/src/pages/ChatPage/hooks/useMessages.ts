// useRoomMessages.ts
import { useInfiniteQuery, useQueryClient } from "@tanstack/react-query";
import { cacheKeys } from "../types";
import type { SimplePage } from "../../../dtos/common/SimplePage";
import type { RoomPreview } from "../../../dtos/room/RoomPreview";
import { sendGetMessagePageRequest } from "../../../utils/api/message";
import { sendUpdateRoomReadStatusRequest } from "../../../utils/api/room";
import type { InfiniteData } from "../../../types/InfiniteData";

export const useRoomMessages = (roomId: string) => {
  const queryClient = useQueryClient();
  const pageSize = 30;

  const updateOptimisticallyRoomPreviewsCache = () => {
    queryClient.setQueryData<InfiniteData<SimplePage<RoomPreview>>>(
      cacheKeys.allRooms,
      (oldData) => {
        if (!oldData) return oldData;
        return {
          ...oldData,
          pages: oldData.pages.map((page) => ({
            ...page,
            content: page.content.map((room) =>
              room.id === roomId
                ? { ...room, hasAnyUnreadMessages: false }
                : room,
            ),
          })),
        };
      },
    );
  };

  const getMessages = async (pageNumber: number) => {
    const result = await sendGetMessagePageRequest(
      roomId,
      pageNumber,
      pageSize,
    );
    if (!result.isSuccess) {
      throw new Error("Failed to load chat history.");
    }
    if (pageNumber === 0) {
      updateOptimisticallyRoomPreviewsCache();
      sendUpdateRoomReadStatusRequest(roomId);
    }
    return result.data;
  };

  const queryResult = useInfiniteQuery({
    queryKey: cacheKeys.messagesFromRoom(roomId),
    queryFn: ({ pageParam = 0 }) => getMessages(pageParam),
    initialPageParam: 0,
    getNextPageParam: (lastPage) => {
      const nextPage = lastPage.pageNumber + 1;
      return nextPage < lastPage.totalPages ? nextPage : undefined;
    },
    enabled: !!roomId,
    staleTime: Infinity,
  });

  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isLoading,
    isError,
  } = queryResult;

  const messages = data?.pages.flatMap((page) => page.content) ?? [];

  return {
    messages,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isLoading,
    isError,
  };
};
