import { useInfiniteQuery } from "@tanstack/react-query";
import { cacheKeys } from "../types";
import { sendGetMessagePageRequest } from "../../../utils/api/message";
import { sendUpdateRoomReadStatusRequest } from "../../../utils/api/room";

export const useRoomMessages = (roomId: string) => {
  const pageSize = 30;

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
    retry: 3,
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
