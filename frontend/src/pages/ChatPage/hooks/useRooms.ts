import { useInfiniteQuery } from "@tanstack/react-query";
import { cacheKeys } from "../types";
import { sendGetRoomPreviewsPageRequest } from "../../../utils/api/room";

export const useChatRooms = (pageSize: number) => {
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
      await new Promise((f) => setTimeout(f, 3000));
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
    retry: 4,
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
