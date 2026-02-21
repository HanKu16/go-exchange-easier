import { Box, useMediaQuery, useTheme } from "@mui/material";
import RoomBox from "./RoomBox";
import { sendGetRoomPageRequest } from "../../utils/api/room";
import LoadingRoomsList from "./LoadingRoomsList";
import NoRoomsBox from "./NoRoomsBox";
import { useInfiniteQuery } from "@tanstack/react-query";
import React from "react";
import { useSnackbar } from "../../context/SnackBarContext";
import ErrorRoomsBox from "./ErrorRoomsBox";

const RoomList = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("md"));
  const pageSize = isMobile ? 15 : 10;
  const { showAlert } = useSnackbar();

  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isLoading,
    isError,
  } = useInfiniteQuery({
    queryKey: ["rooms"],
    queryFn: async ({ pageParam = 0 }) => {
      await new Promise((f) => setTimeout(f, 3000));
      const result = await sendGetRoomPageRequest(pageParam, pageSize);
      if (!result.isSuccess) {
        showAlert("Failed to load conversations.", "error");
        throw new Error("Failed to load rooms.");
      }
      return result.data;
    },
    initialPageParam: 0,
    getNextPageParam: (lastPage) => {
      const nextPage = lastPage.pageNumber + 1;
      return nextPage < lastPage.totalPages ? nextPage : undefined;
    },
    retry: 5,
    retryDelay: (attemptIndex) => Math.min(1000 * 2 ** attemptIndex, 30000),
  });

  const rooms =
    data?.pages.flatMap((page) =>
      page.content.map((r) => ({
        id: r.id,
        name: r.name,
        avatarUrl: r.imageUrl,
        lastMessage: r.lastMessage,
      })),
    ) ?? [];

  const handleScroll = (e: React.UIEvent<HTMLDivElement>) => {
    const { scrollTop, scrollHeight, clientHeight } = e.currentTarget;
    const isAtBottom = Math.abs(scrollHeight - scrollTop - clientHeight) < 5;

    if (isAtBottom && hasNextPage && !isFetchingNextPage) {
      fetchNextPage();
    }
  };

  if (isLoading) {
    return (
      <Box sx={listStyles}>
        <LoadingRoomsList numberOfBoxes={12} />
      </Box>
    );
  } else if (isError) {
    return <ErrorRoomsBox />;
  } else if (rooms.length === 0) {
    return <NoRoomsBox />;
  }
  return (
    <Box sx={listStyles} onScroll={handleScroll}>
      {rooms.map((props) => (
        <RoomBox key={props.id} {...props} />
      ))}
      {isFetchingNextPage && <LoadingRoomsList numberOfBoxes={2} />}
    </Box>
  );
};

const listStyles = {
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
};

export default RoomList;
