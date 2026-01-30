import { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import type { ReactElement } from "react";
import { Radio, RadioGroup } from "@mui/material";
import { FormControl, Button } from "@mui/material";
import { sendUpdateStatusRequest } from "../utils/user";
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
      allStatuses.push({ id: 0, name: "UNKNOWN" });
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
    const statusId = selectedStatusId !== 0 ? selectedStatusId : null;
    const result = await sendUpdateStatusRequest({ statusId: statusId });
    if (result.isSuccess) {
      showAlert("Status was updated successfully.", "success");
    } else {
      showAlert("Failed to update status. Please try again later.", "error");
      setShowConfirmButton(true);
    }
  };

  const getInteractionElement = (statusId: number): ReactElement => {
    if (showConfirmButton && statusId === selectedStatusId) {
      return (
        <Button
          variant="contained"
          size="small"
          sx={{ marginLeft: 2 }}
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
          <FormControl>
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
                  control={<Radio />}
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
