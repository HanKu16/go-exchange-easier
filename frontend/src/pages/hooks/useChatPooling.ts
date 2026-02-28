import { useEffect } from "react";
import useChatSync from "./useChatSync";

export const useChatPolling = (roomId: string) => {
  const { syncAll } = useChatSync(roomId);

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
