import { useQueryClient } from "@tanstack/react-query";
import { useCallback } from "react";
import { cacheKeys } from "../types";

const useChatSync = (roomId: string | undefined) => {
  const queryClient = useQueryClient();

  const syncAll = useCallback(async () => {
    const tasks = [
      queryClient.invalidateQueries({ queryKey: cacheKeys.allRooms }),
    ];
    if (roomId) {
      tasks.push(
        queryClient.invalidateQueries({
          queryKey: cacheKeys.messagesFromRoom(roomId),
        }),
      );
    }
    await Promise.all(tasks);
  }, [roomId, queryClient]);

  return { syncAll };
};

export default useChatSync;
