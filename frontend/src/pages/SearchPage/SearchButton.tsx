import { Button } from "@mui/material";

type SearchButtonProps = {
  onClick: () => void;
};

const SearchButton = (props: SearchButtonProps) => {
  return (
    <Button
      onClick={props.onClick}
      variant="contained"
      sx={{
        borderRadius: "50px",
        px: 4,
        py: 1.5,
        fontWeight: 700,
        bgcolor: "#182c44",
        "&:hover": { bgcolor: "#244164" },
        ml: 1,
      }}
    >
      Search
    </Button>
  );
};

export default SearchButton;
