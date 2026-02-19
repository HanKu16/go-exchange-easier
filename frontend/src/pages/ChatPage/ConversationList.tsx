import { Box } from "@mui/material";
import ConversationBox from "./ConservationBox";
import { useEffect, useRef, useState } from "react";
import { sendGetRoomPageRequest } from "../../utils/api/room";
import type { ConversationBoxProps } from "./types";
import LoadingConversationList from "./LoadingConversationList";
import NoConversationsBox from "./NoConversationBox";

const ConversationList = () => {
  const [converstationBoxPropses, setConversationBoxPropses] = useState<
    ConversationBoxProps[]
  >([]);
  const [isLoadingNextPage, setIsLoadingNextPage] = useState(true);
  const lastFetchedPage = useRef<number | null>(null);
  const pageSize = 10;
  const [totalConverations, setTotalConverations] = useState<number | null>(
    null,
  );

  const getNextPage = async () => {
    if (!isLoadingNextPage) {
      setIsLoadingNextPage(true);
    }
    const nextPageNumber = getNextPageNumber();
    await new Promise((f) => setTimeout(f, 3000));
    const result = await sendGetRoomPageRequest(nextPageNumber, pageSize);
    if (result.isSuccess) {
      const nextPageConversationBoxPropses = result.data.content.map((r) => {
        return {
          id: r.id,
          name: r.name,
          avatarUrl: r.imageUrl,
          lastMessage: r.lastMessage,
        };
      });
      setTotalConverations(result.data.totalElements);
      setConversationBoxPropses((prev) => [
        ...prev,
        ...nextPageConversationBoxPropses,
      ]);
      lastFetchedPage.current = nextPageNumber;
    }
    setIsLoadingNextPage(false);
  };

  const getNextPageNumber = (): number => {
    if (lastFetchedPage.current === null) {
      return 0;
    }
    return lastFetchedPage.current + 1;
  };

  const shouldLoadNextPage = (): boolean => {
    return (
      !isLoadingNextPage &&
      (totalConverations === null ||
        converstationBoxPropses.length < totalConverations)
    );
  };

  const handleScroll = (e: React.UIEvent<HTMLDivElement>) => {
    const { scrollTop, scrollHeight, clientHeight } = e.currentTarget;
    const isAtBottom = Math.abs(scrollHeight - scrollTop - clientHeight) < 1;

    if (isAtBottom && shouldLoadNextPage()) {
      getNextPage();
    }
  };

  const getContent = () => {
    if (totalConverations === 0) {
      return <NoConversationsBox />;
    }
    return (
      <>
        {converstationBoxPropses.map((props: ConversationBoxProps) => (
          <ConversationBox key={props.id} {...props} />
        ))}
        {isLoadingNextPage && <LoadingConversationList numberOfBoxes={2} />}
        {totalConverations === null && (
          <LoadingConversationList numberOfBoxes={12} />
        )}
      </>
    );
  };

  useEffect(() => {
    getNextPage();
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
      {getContent()}
    </Box>
  );
};

export default ConversationList;
