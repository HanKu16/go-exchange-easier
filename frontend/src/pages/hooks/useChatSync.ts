import { useQueryClient } from "@tanstack/react-query";
import { useCallback } from "react";
import { cacheKeys } from "../ChatPage/types";

export const useChatSync = (roomId: string) => {
  const queryClient = useQueryClient();

  const syncAll = useCallback(async () => {
    if (!roomId) return;
    await Promise.all([
      queryClient.invalidateQueries({
        queryKey: cacheKeys.messagesFromRoom(roomId),
      }),
      queryClient.invalidateQueries({ queryKey: cacheKeys.allRooms }),
    ]);
  }, [roomId, queryClient]);

  return { syncAll };
};

export default useChatSync;
