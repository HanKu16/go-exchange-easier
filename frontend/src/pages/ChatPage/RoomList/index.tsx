import { useMediaQuery, useTheme, Box } from "@mui/material";
import ErrorBox from "./ErrorBox";
import LoadingListBox from "./LoadingListBox";
import NoRooms from "./NoContentBox";
import RoomPreviewBox from "./RoomPreviewBox";
import useChatRooms from "../hooks/useRooms";
import LoadingError from "./LoadingError";

const RoomList = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("lg"));
  const pageSize = isMobile ? 15 : 10;
  const {
    rooms,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isError,
    isLoading,
  } = useChatRooms(pageSize);

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
      {isError && !isFetchingNextPage && (
        <LoadingError onRetry={fetchNextPage} />
      )}
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
