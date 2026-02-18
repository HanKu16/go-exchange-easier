import { Box } from "@mui/material";
import ConversationBox from "./ConservationBox";
import { useEffect, useState } from "react";
import { sendGetRoomPageRequest } from "../../utils/api/room";
import type { ConversationBoxProps } from "./types";

const ConversationList = () => {
  const [converstationBoxPropses, setConversationBoxPropses] = useState<
    ConversationBoxProps[]
  >([]);

  const getConservationsPage = async () => {
    const result = await sendGetRoomPageRequest(0, 10);
    if (result.isSuccess) {
      setConversationBoxPropses(
        result.data.content.map((r) => {
          return {
            id: r.id,
            name: r.name,
            avatarUrl: r.imageUrl,
            lastMessage: r.lastMessage,
          };
        }),
      );
    } else {
      console.error(result.error);
    }
  };

  const handleScroll = (e: React.UIEvent<HTMLDivElement>) => {
    const { scrollTop, scrollHeight, clientHeight } = e.currentTarget;
    const isAtBottom = Math.abs(scrollHeight - scrollTop - clientHeight) < 1;

    if (isAtBottom) {
      console.log("Jesteś na samym dole!");
    } else {
      console.log("Użytkownik przewinął do góry");
    }
  };

  useEffect(() => {
    getConservationsPage();
  }, []);

  return (
    <Box
      sx={{
        width: { xs: "100%", md: "30%", lg: "25%" },
        height: "100%",
        display: "flex",
        flexDirection: "column",
        backgroundColor: "#e8e5e5",
        gap: 1,
        alignItems: "center",
        paddingY: 2,
        overflowY: "auto",
        borderRight: "1px solid #e0e0e0",
      }}
      onScroll={handleScroll}
    >
      {converstationBoxPropses.map((props: ConversationBoxProps) => (
        <ConversationBox key={props.id} {...props} />
      ))}
    </Box>
  );
};

export default ConversationList;
