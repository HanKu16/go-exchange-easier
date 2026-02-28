import { useMutation } from "@tanstack/react-query";
import useChatSync from "./useChatSync";
import { sendDeleteMessageRequest } from "../../../utils/api/message";

const useDeleteMessage = (roomId: string | undefined) => {
  const { syncAll } = useChatSync(roomId);

  const mutation = useMutation({
    mutationFn: async (messageId: string) => {
      if (!roomId) {
        throw new Error("Room ID is required.");
      }
      const result = await sendDeleteMessageRequest(roomId, messageId);
      if (!result.isSuccess) {
        throw new Error("Failed to delete message.");
      }

      return messageId;
    },
    onSettled: () => {
      syncAll();
    },
  });

  const deleteMessage = async (messageId: string): Promise<boolean> => {
    try {
      await mutation.mutateAsync(messageId);
      return true;
    } catch (error) {
      return false;
    }
  };

  return {
    deleteMessage,
  };
};

export default useDeleteMessage;
