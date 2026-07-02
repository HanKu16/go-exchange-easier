import { useState } from "react";
import Box from "@mui/material/Box";
import { IconButton, Tooltip } from "@mui/material";
import { Button } from "@mui/material";
import { sendUpdateNickRequest } from "../utils/api/user";
import { TextField } from "@mui/material";
import InfoIcon from "@mui/icons-material/Info";
import PanelHeader from "../components/PanelHeader";
import { useSnackbar } from "../context/SnackBarContext";

const UpdateUserNickPanel = () => {
  const maxNickSize = 20; 
  const minNickSize = 3; 
  
  const [savedNick, setSavedNick] = useState<string | undefined>(
    undefined,
  );
  const [nick, setNick] = useState<string>("");
  const { showAlert } = useSnackbar();
  const [isUpdating, setIsUpdating] = useState<boolean>(false);
  
  const isTooLong = nick.length > maxNickSize;
  const isTooShort = nick.length < minNickSize;
  
  const showConfirmButton =
    !isTooLong && !isTooShort && !isUpdating && nick !== savedNick;

  const handleNickUpdate = async () => {
    setIsUpdating(true);
    showAlert("Waiting for server response.", "info");
    const result = await sendUpdateNickRequest({
      nick: nick,
    });
    if (result.isSuccess) {
      showAlert("Nick was updated successfully.", "success");
      setSavedNick(nick);
    } else {
      const hasError = (code: string) => 
        result.error.fieldErrors?.some((e: any) => e.code === code);

      if (hasError("NOT_BLANK")) {
        showAlert("Nick can not be empty or consist only of spaces.", "error");
      } else if (hasError("SIZE")) {
        showAlert(
          `Nick must be between ${minNickSize} and ${maxNickSize} characters. Can contain only letters and numbers. Current length: ${nick.length}.`,
          "error",
        );
      } else if (hasError("PATTERN")) {
        showAlert("Nick contains invalid characters. Please use a valid format.", "error");
      } else {
        showAlert(
          "Failed to update nick. Please try again later.",
          "error",
        );
      }
    }
    setIsUpdating(false);
  };

  return (
    <Box>
      <Box sx={{ display: "flex", alignItems: "center" }}>
        <PanelHeader label="Update your nick" />
        <Tooltip
          title={`Nick must be between ${minNickSize} and ${maxNickSize} characters 
          (currently ${nick.length}).`}
          sx={{ paddingBottom: 3 }}
        >
          <IconButton>
            <InfoIcon />
          </IconButton>
        </Tooltip>
      </Box>

      <TextField
        id="nick-input"
        label="Nick"
        placeholder="Enter your nick"
        sx={{
          width: "100%",
          marginTop: 2,
          "& .MuiOutlinedInput-root": {
            "& fieldset": { borderColor: nick ? "#182c44" : "#e0e0e0" },
            "&:hover fieldset": { borderColor: nick ? "#182c44" : "#bdbdbd" },
            "&.Mui-focused fieldset": { borderColor: nick ? "#182c44" : "#3f51b5" },
          },
          "& .MuiInputLabel-root": {
            color: nick ? "#182c44" : undefined,
          },
          "& .MuiInputLabel-root.Mui-focused": {
            color: "#182c44",
          },
        }}
        value={nick}
        onChange={(event) => setNick(event.target.value)}
      />
      {showConfirmButton && (
        <Button
          variant="contained"
          size="large"
          sx={{ marginTop: 2, backgroundColor: "#182c44", "&:hover": { backgroundColor: "#244164" } }}
          onClick={handleNickUpdate}
        >
          CONFIRM
        </Button>
      )}
    </Box>
  );
};

export default UpdateUserNickPanel;