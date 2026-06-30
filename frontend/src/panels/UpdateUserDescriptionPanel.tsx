import { useState } from "react";
import Box from "@mui/material/Box";
import { IconButton, Tooltip } from "@mui/material";
import { Button } from "@mui/material";
import { sendUpdateDescriptionRequest } from "../utils/api/user";
import { TextField } from "@mui/material";
import InfoIcon from "@mui/icons-material/Info";
import PanelHeader from "../components/PanelHeader";
import { useSnackbar } from "../context/SnackBarContext";

const UpdateUserDescriptionPanel = () => {
  const maxDescriptionSize = 500;
  const [savedDescription, setSavedDescription] = useState<string | undefined>(
    undefined,
  );
  const [description, setDescription] = useState<string>("");
  const { showAlert } = useSnackbar();
  const [isUpdating, setIsUpdating] = useState<boolean>(false);
  const isTooLong = description.length > maxDescriptionSize;
  const showConfirmButton =
    !isTooLong && !isUpdating && description !== savedDescription;

  const handleDescriptionUpdate = async () => {
    setIsUpdating(true);
    showAlert("Waiting for server response.", "info");
    const result = await sendUpdateDescriptionRequest({
      description: description,
    });
    if (result.isSuccess) {
      showAlert("Description was updated successfully.", "success");
      setSavedDescription(description);
    } else {
      if (result.error.fieldErrors.some((e) => e.code === "SIZE")) {
        showAlert(
          `Description can not be longer 
          than ${maxDescriptionSize} characters. But current has 
          ${description.length}.`,
          "error",
        );
      } else {
        showAlert(
          "Failed to update description. Please try again later.",
          "error",
        );
      }
    }
    setIsUpdating(false);
  };

  return (
    <Box>
      <Box sx={{ display: "flex", alignItems: "center" }}>
        <PanelHeader label="Update your description" />
        <Tooltip
          title={`Description can not be longer than ${maxDescriptionSize} characters 
          (currently ${description.length}).`}
          sx={{ paddingBottom: 3 }}
        >
          <IconButton>
            <InfoIcon />
          </IconButton>
        </Tooltip>
      </Box>

      <TextField
        id="description-input"
        label="Description"
        multiline
        rows={6}
        placeholder="Enter your description"
        sx={{
          width: "100%",
          marginTop: 2,
          "& .MuiOutlinedInput-root": {
            "& fieldset": { borderColor: description ? "#182c44" : "#e0e0e0" },
            "&:hover fieldset": { borderColor: description ? "#182c44" : "#bdbdbd" },
            "&.Mui-focused fieldset": { borderColor: description ? "#182c44" : "#3f51b5" },
          },
          "& .MuiInputLabel-root": {
            color: description ? "#182c44" : undefined,
          },
          "& .MuiInputLabel-root.Mui-focused": {
            color: "#182c44",
          },
        }}
        value={description}
        onChange={(event) => setDescription(event.target.value)}
      />
      {showConfirmButton && (
        <Button
          variant="contained"
          size="large"
          sx={{ marginTop: 2, backgroundColor: "#182c44", "&:hover": { backgroundColor: "#244164" } }}
          onClick={handleDescriptionUpdate}
        >
          CONFIRM
        </Button>
      )}
    </Box>
  );
};

export default UpdateUserDescriptionPanel;
