import LoadingConversationBox from "./LoadingConversationBox";

const LoadingConversationList = ({
  numberOfBoxes,
}: {
  numberOfBoxes: number;
}) => {
  return (
    <>
      {[...Array(numberOfBoxes)].map((_) => (
        <LoadingConversationBox />
      ))}
    </>
  );
};

export default LoadingConversationList;
