import { Container } from "@mui/material";
import MessageBox from "./MessageBox";
import LoadingBox from "./LoadingBox";
import LoadingError from "./LoadingError";
import { useParams } from "react-router-dom";
import { useSignedInUser } from "../../../context/SignedInUserContext";
import { useRoomMessages } from "../hooks/useMessages";
import useDeleteMessage from "../hooks/useDeleteMessage";

const MessagesContainer = () => {
  const { roomId } = useParams();
  const temporaryMessagePrefix = "temp-";
  const { signedInUser } = useSignedInUser();

  const handleScroll = (e: React.UIEvent<HTMLDivElement>) => {
    const { scrollTop, scrollHeight, clientHeight } = e.currentTarget;
    const threshold = 300;
    const isNearTop =
      Math.abs(scrollTop) + clientHeight >= scrollHeight - threshold;
    if (isNearTop && hasNextPage && !isFetchingNextPage && !isError) {
      fetchNextPage();
    }
  };

  const {
    messages,
    fetchNextPage,
    isLoading,
    hasNextPage,
    isFetchingNextPage,
    isError,
  } = useRoomMessages(roomId!);

  const { deleteMessage } = useDeleteMessage(roomId);

  return (
    <Container
      sx={{
        flexGrow: 1,
        width: "100%",
        display: "flex",
        flexDirection: "column-reverse",
        backgroundColor: "#dedede",
        alignItems: "center",
        boxShadow: 3,
        bgcolor: "background.paper",
        overflowY: "auto",
        paddingBottom: { xs: 1, md: 2 },
      }}
      onScroll={handleScroll}
    >
      {messages.map((m) => (
        <MessageBox
          id={m.id}
          textContent={m.textContent}
          nick={m.author.nick}
          avatarUrl={m.author.avatarUrl}
          dateAndTime={m.createdAt}
          isUserMessage={m.author.id === signedInUser.id}
          isPending={m.id.startsWith(temporaryMessagePrefix) ? true : false}
          onDelete={async () => await deleteMessage(m.id)}
          key={m.id}
        />
      ))}
      {(isLoading || isFetchingNextPage) && <LoadingBox />}
      {isError && !isLoading && <LoadingError onRetry={fetchNextPage} />}
    </Container>
  );
};

export default MessagesContainer;
