import { useState } from "react";
import Box from "@mui/material/Box";
import { IconButton, Tooltip } from "@mui/material";
import { Button } from "@mui/material";
import { sendUpdateDescriptionRequest } from "../utils/user";
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
      if (result.error.fieldErrors.some((e) => e.code === "Size")) {
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
        sx={{ width: "100%", marginTop: 2 }}
        value={description}
        onChange={(event) => setDescription(event.target.value)}
      />
      {showConfirmButton && (
        <Button
          variant="contained"
          size="large"
          sx={{ marginTop: 2 }}
          onClick={handleDescriptionUpdate}
        >
          CONFIRM
        </Button>
      )}
    </Box>
  );
};

export default UpdateUserDescriptionPanel;
