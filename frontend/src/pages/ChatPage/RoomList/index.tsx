import { useInfiniteQuery } from "@tanstack/react-query";
import { useMediaQuery, useTheme, Box } from "@mui/material";
import { useSnackbar } from "../../../context/SnackBarContext";
import { sendGetRoomPreviewsPageRequest } from "../../../utils/api/room";
import ErrorBox from "./ErrorBox";
import LoadingListBox from "./LoadingListBox";
import NoRooms from "./NoContentBox";
import RoomPreviewBox from "./RoomPreviewBox";
import { cacheKeys } from "../types";

const RoomList = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("lg"));
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
    queryKey: cacheKeys.allRooms,
    queryFn: async ({ pageParam = 0 }) => {
      await new Promise((f) => setTimeout(f, 3000));
      const result = await sendGetRoomPreviewsPageRequest(pageParam, pageSize);
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
    retry: 4,
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

    if (isAtBottom && hasNextPage && !isFetchingNextPage && !isError) {
      fetchNextPage();
    }
  };

  if (isLoading) {
    return (
      <Box sx={listStyles}>
        <LoadingListBox numberOfBoxes={pageSize} />
      </Box>
    );
  } else if (isError && rooms.length === 0) {
    return (
      <Box sx={listStyles}>
        <ErrorBox />;
      </Box>
    );
  } else if (rooms.length === 0) {
    return (
      <Box sx={listStyles}>
        <NoRooms />
      </Box>
    );
  }
  return (
    <Box sx={listStyles} onScroll={handleScroll}>
      {rooms.map((props) => (
        <RoomPreviewBox key={props.id} {...props} />
      ))}
      {isFetchingNextPage && <LoadingListBox numberOfBoxes={2} />}
      {isError && <></>}
    </Box>
  );
};

const listStyles = {
  minWidth: { md: "30%", lg: "25%" },
  maxWidth: { md: "30%", lg: "25%" },
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
