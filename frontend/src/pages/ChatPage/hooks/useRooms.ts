import { useInfiniteQuery, useQueryClient } from "@tanstack/react-query";
import { cacheKeys } from "../types";
import { sendGetRoomPreviewsPageRequest } from "../../../utils/api/room";
import { useEffect } from "react";
import type { SimplePage } from "../../../dtos/common/SimplePage";
import type { RoomPreview } from "../../../dtos/room/RoomPreview";
import type { InfiniteData } from "../../../types/InfiniteData";
import { useParams } from "react-router-dom";

export const useChatRooms = (pageSize: number) => {
  const { roomId } = useParams();
  const queryClient = useQueryClient();
  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isLoading,
    isError,
  } = useInfiniteQuery({
    queryKey: cacheKeys.allRooms,
    queryFn: async ({ pageParam = 0 }) => {
      // await new Promise((f) => setTimeout(f, 1000));
      const result = await sendGetRoomPreviewsPageRequest(pageParam, pageSize);
      if (!result.isSuccess) {
        throw new Error("Failed to load rooms.");
      }
      return result.data;
    },
    initialPageParam: 0,
    getNextPageParam: (lastPage) => {
      const nextPage = lastPage.pageNumber + 1;
      return nextPage < lastPage.totalPages ? nextPage : undefined;
    },
    retry: 3,
    retryDelay: (attemptIndex) => Math.min(1000 * 2 ** attemptIndex, 30000),
    refetchIntervalInBackground: false,
    refetchOnWindowFocus: true,
    select: (data) => ({
      ...data,
      pages: data.pages.flatMap((page) =>
        page.content.map((r) => ({
          id: r.id,
          name: r.name,
          hasAnyUnreadMessages: r.hasAnyUnreadMessages,
          avatarUrl: r.imageUrl,
          lastMessage: r.lastMessage,
        })),
      ),
    }),
  });

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

  useEffect(() => {
    if (data && roomId) {
      updateOptimisticallyRoomPreviewsCache();
    }
  }, [data, roomId]);

  const rooms = data?.pages ?? [];

  return {
    rooms,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isLoading,
    isError,
  };
};

export default useChatRooms;
