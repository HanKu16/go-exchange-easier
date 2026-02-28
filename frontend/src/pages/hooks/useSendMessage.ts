import {
  useMutation,
  useQueryClient,
  type InfiniteData,
  type QueryKey,
} from "@tanstack/react-query";
import { cacheKeys, temporaryMessagePrefix } from "../ChatPage/types";
import { sendCreateMessageRequest } from "../../utils/api/message";
import { useSignedInUser } from "../../context/SignedInUserContext";
import type { SimplePage } from "../../dtos/common/SimplePage";
import type { MessageDetails } from "../../dtos/message/MessageDetails";

export const useSendMessage = (roomId: string, syncAll: () => void) => {
  const queryClient = useQueryClient();
  const { signedInUser } = useSignedInUser();

  const sendMessage = async (messageText: string) => {
    const result = await sendCreateMessageRequest(roomId, {
      textContent: messageText,
    });
    await new Promise((f) => setTimeout(f, 1500));
    if (!result.isSuccess) {
      throw new Error("Failed to create message.");
    }
  };

  const createOptimisticMessage = (messageText: string): MessageDetails => {
    return {
      id: `${temporaryMessagePrefix}-${Date.now()}`,
      textContent: messageText,
      createdAt: new Date().toISOString(),
      author: {
        id: signedInUser.id,
        nick: "You",
        avatarUrl: signedInUser.avatarUrl,
      },
    };
  };

  const updateCacheWithOptimisticMessage = (
    optimisticMessage: MessageDetails,
    queryKey: QueryKey,
  ): void => {
    queryClient.setQueryData<InfiniteData<SimplePage<MessageDetails>>>(
      queryKey,
      (oldData) => {
        if (!oldData) return oldData;
        return {
          ...oldData,
          pages: oldData.pages.map((page, index) =>
            index === 0
              ? { ...page, content: [optimisticMessage, ...page.content] }
              : page,
          ),
        };
      },
    );
  };

  const getPreviousData = (queryKey: QueryKey) => {
    return queryClient.getQueryData<InfiniteData<SimplePage<MessageDetails>>>(
      queryKey,
    );
  };

  const { mutate } = useMutation({
    mutationFn: (newText: string) => sendMessage(newText),
    onMutate: async (newText) => {
      if (!roomId) {
        return;
      }
      const queryKey = cacheKeys.messagesFromRoom(roomId);
      await queryClient.cancelQueries({ queryKey });
      const previousData = getPreviousData(queryKey);
      const optimisticMessage: MessageDetails =
        createOptimisticMessage(newText);
      updateCacheWithOptimisticMessage(optimisticMessage, queryKey);
      return { previousData };
    },
    onError: (_, __, context) => {
      if (context?.previousData && roomId) {
        queryClient.setQueryData(
          cacheKeys.messagesFromRoom(roomId),
          context.previousData,
        );
      }
    },
    onSettled: () => {
      if (!roomId) {
        return;
      }
      syncAll();
    },
  });
  return { sendMessage: mutate };
};

export default useSendMessage;
