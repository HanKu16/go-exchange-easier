import { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import type { ReactElement } from "react";
import { Radio, RadioGroup } from "@mui/material";
import { FormControl, Button } from "@mui/material";
import { sendUpdateStatusRequest } from "../utils/api/user";
import { sendGetUserStatusesRequest } from "../utils/api/user-status";
import { FormControlLabel } from "@mui/material";
import type { UserStatusSummary } from "../dtos/user/status/UserStatusSummary";
import PanelHeader from "../components/PanelHeader";
import { useSnackbar } from "../context/SnackBarContext";
import type { DataFetchStatus } from "../types/DataFetchStatus";
import LoadingContent from "../components/LoadingContent";
import ContentLoadError from "../components/ContentLoadError";

const UpdateUserStatusPanel = () => {
  const [statuses, setStatuses] = useState<UserStatusSummary[]>([]);
  const [selectedStatusId, setSelectedStatusId] = useState<number | null>(null);
  const [showConfirmButton, setShowConfirmButton] = useState<boolean>(false);
  const { showAlert } = useSnackbar();
  const [statuesFetchStatus, setStatuesFetchStatus] =
    useState<DataFetchStatus>("loading");

  const getStatuses = async () => {
    const result = await sendGetUserStatusesRequest();
    if (result.isSuccess) {
      const allStatuses = result.data.content;
      setStatuses(allStatuses);
      setStatuesFetchStatus("success");
    } else {
      switch (result.error.status) {
        case "INTERNAL_SERVER_ERROR":
          setStatuesFetchStatus("serverError");
          break;
        case "SERVICE_UNAVAILABLE":
          setStatuesFetchStatus("connectionError");
          break;
      }
    }
  };

  useEffect(() => {
    getStatuses();
  }, []);

  useEffect(() => {
    setShowConfirmButton(true);
  }, [selectedStatusId]);

  const handleStatusUpdate = async () => {
    setShowConfirmButton(false);
    showAlert("Waiting for server response.", "info");
    const result = await sendUpdateStatusRequest({ statusId: selectedStatusId });
    if (result.isSuccess) {
      showAlert("Status was updated successfully.", "success");
    } else {
      showAlert("Failed to update status. Please try again later.", "error");
      setShowConfirmButton(true);
    }
  };

  const handleClearStatus = async () => {
    showAlert("Waiting for server response.", "info");
    const result = await sendUpdateStatusRequest({ statusId: null }); // Wysyłamy nulla
    if (result.isSuccess) {
      showAlert("Status was cleared successfully.", "success");
      setSelectedStatusId(null);
      setShowConfirmButton(false);
    } else {
      showAlert("Failed to clear status. Please try again later.", "error");
    }
  };

  const getInteractionElement = (statusId: number): ReactElement => {
    if (showConfirmButton && statusId === selectedStatusId) {
      return (
        <Button
          variant="contained"
          size="small"
          sx={{ marginLeft: 2, backgroundColor: "#182c44", "&:hover": { backgroundColor: "#244164" } }}
          onClick={handleStatusUpdate}
        >
          CONFIRM
        </Button>
      );
    }
    return <></>;
  };

  const getContent = () => {
    switch (statuesFetchStatus) {
      case "success":
        return (
          <>
            <Box
              sx={{
                display: "flex",
                justifyContent: "flex-start",
                ml: 2,
                mr: 2,
                mt: 2,
                mb: 1,
              }}
            >
              <Button
                variant="outlined"
                color="error"
                onClick={handleClearStatus}
                sx={{
                  borderRadius: "12px",
                  textTransform: "none",
                  width: { xs: "100%", sm: "auto" }
                }}
              >
                Clear assigned status
              </Button>
            </Box>

            <FormControl sx={{ ml: 2, mt: 0 }}>
              <RadioGroup
                name="statuses-buttons-group"
                value={selectedStatusId}
                onChange={(event) => {
                  setSelectedStatusId(Number(event.target.value));
                }}
              >
                {statuses.map((s) => (
                  <FormControlLabel
                    key={s.id}
                    value={s.id}
                    control={<Radio sx={{ '&.Mui-checked': { color: '#182c44' } }} />}
                    label={
                      <>
                        {s.name}
                        {getInteractionElement(s.id)}
                      </>
                    }
                  />
                ))}
              </RadioGroup>
            </FormControl>
          </>
        );
      case "loading":
        return <LoadingContent title="Loading statuses" />;
      case "connectionError":
        return (
          <ContentLoadError
            title="Connection error"
            subheader="Failed to load statuses"
          />
        );
      case "serverError":
        return (
          <ContentLoadError
            title="Server error"
            subheader="Failed to load statuses"
          />
        );
    }
  };

  return (
    <Box>
      <PanelHeader label="Assign status that fits you the most" />
      {getContent()}
    </Box>
  );
};

export default UpdateUserStatusPanel;
