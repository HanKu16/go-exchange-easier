import { useEffect } from "react";
import useChatSync from "./useChatSync";

export const useChatPolling = (roomId: string | undefined) => {
  const { syncAll } = useChatSync(roomId);

  useEffect(() => {
    syncAll();
    const interval = setInterval(() => {
      syncAll();
    }, 30000);

    return () => clearInterval(interval);
  }, [roomId, syncAll]);

  return {};
};
