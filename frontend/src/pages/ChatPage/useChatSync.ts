import { useQueryClient } from "@tanstack/react-query";
import { useCallback, useEffect } from "react";
import { cacheKeys } from "./types";

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

  useEffect(() => {
    if (!roomId) {
      return;
    }

    syncAll();
    const interval = setInterval(() => {
      syncAll();
    }, 30000);

    return () => clearInterval(interval);
  }, [roomId, syncAll]);

  return { syncAll };
};
