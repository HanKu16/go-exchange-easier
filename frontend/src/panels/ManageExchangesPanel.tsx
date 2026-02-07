import PanelHeader from "../components/PanelHeader";
import {
  Box,
  Button,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  useMediaQuery,
  useTheme,
} from "@mui/material";
import { useState } from "react";
import NoContent from "../components/NoContent";
import { useEffect } from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import {
  sendDeleteExchangeRequest,
  sendGetExchangesRequest,
} from "../utils/api/exchange";
import type { AlertMessage } from "../types/AlertMessage";
import { useSnackbar } from "../context/SnackBarContext";
import type { DataFetchStatus } from "../types/DataFetchStatus";
import LoadingContent from "../components/LoadingContent";
import ContentLoadError from "../components/ContentLoadError";
import { useConfirmation } from "../context/ConfirmationDialogContext";

type ActionExchangeTableProps = {
  exchanges: {
    id: number;
    timeRange: {
      startedAt: string;
      endAt: string;
    };
    university: {
      id: number;
      name: string;
    };
    universityMajorName: string;
    city: {
      name: string;
      countryName: string;
    };
  }[];
  message: AlertMessage | null;
  setMessage: (message: AlertMessage) => void;
  setDeletedExchangeId: (exchangeId: number) => void;
};

type ManageExchangesPanelProps = {
  userId: number | string;
};

const ActionExchangeTableProps = (props: ActionExchangeTableProps) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("lg"));
  const { showAlert } = useSnackbar();
  const { showConfirmation } = useConfirmation();

  const handleDeletion = async (exchangeId: number) => {
    showConfirmation({
      title: "Are you sure you want to delete this exchange?",
      message: "This action cannot be undone.",
      onConfirm: async () => {
        showAlert("Waiting for server response.", "info");
        const response = await sendDeleteExchangeRequest(exchangeId);
        if (response.isSuccess) {
          showAlert("Exchange was deleted successfully.", "success");
          props.setDeletedExchangeId(exchangeId);
        } else {
          showAlert("Failed to delete exchange.", "error");
        }
      },
      confirmColor: "error",
    });
  };

  return (
    <Box sx={{ display: "flex", margin: "auto", width: "91%" }}>
      {!isMobile ? (
        <TableContainer
          component={Paper}
          sx={{ boxShadow: 4, borderRadius: 3, overflow: "hidden" }}
        >
          <Table>
            <TableHead>
              <TableRow sx={{ backgroundColor: "#04315fff" }}>
                {[
                  "University",
                  "Major",
                  "City",
                  "Started at",
                  "End at",
                  "",
                ].map((header, index) => (
                  <TableCell
                    key={index}
                    align={"center"}
                    sx={{
                      fontWeight: "bold",
                      color: "white",
                      textTransform: "uppercase",
                      letterSpacing: "0.05em",
                      fontSize: "0.9rem",
                    }}
                  >
                    {header}
                  </TableCell>
                ))}
              </TableRow>
            </TableHead>
            <TableBody>
              {props.exchanges.map((e) => (
                <TableRow
                  key={e.id}
                  sx={{
                    "&:last-child td, &:last-child th": { border: 0 },
                    "&:nth-of-type(odd)": { backgroundColor: "#f9f9f9" },
                    "&:hover": {
                      backgroundColor: "#e3f2fd",
                      transform: "scale(1.01)",
                      transition: "0.2s ease-in-out",
                    },
                  }}
                >
                  <TableCell align="center">
                    <Typography fontWeight="600">
                      {e.university.name}
                    </Typography>
                  </TableCell>
                  <TableCell align="center">{e.universityMajorName}</TableCell>
                  <TableCell align="center">
                    {e.city.name}
                    <img
                      src={`/flags/${e.city.countryName}.png`}
                      alt={`${e.city.countryName} flag`}
                      style={{ height: "0.8rem", marginLeft: 2 }}
                    />
                  </TableCell>
                  <TableCell align="center">{e.timeRange.startedAt}</TableCell>
                  <TableCell align="center">{e.timeRange.endAt}</TableCell>
                  <TableCell align="center">
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      startIcon={<DeleteIcon />}
                      onClick={() => {
                        handleDeletion(e.id);
                      }}
                    >
                      Delete
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Box sx={{ width: "100%", display: "grid", gap: 2 }}>
          {props.exchanges.map((e) => (
            <Paper
              key={e.id}
              sx={{
                p: 2,
                borderRadius: 2,
                boxShadow: 3,
                "&:hover": { backgroundColor: "#f9f9f9" },
              }}
            >
              <Typography variant="h6" fontWeight="600" gutterBottom>
                {e.university.name}
              </Typography>
              <Typography variant="body2">
                <strong>Major:</strong> {e.universityMajorName}
              </Typography>
              <Typography variant="body2">
                <strong>City:</strong> {e.city.name}
                <img
                  src={`/flags/${e.city.countryName}.png`}
                  alt={`${e.city.countryName} flag`}
                  style={{ height: "0.8rem", marginLeft: 2 }}
                />
              </Typography>
              <Typography variant="body2">
                <strong>Started:</strong> {e.timeRange.startedAt}
              </Typography>
              <Typography variant="body2">
                <strong>Ended:</strong> {e.timeRange.endAt}
              </Typography>
              <Button
                sx={{ marginTop: 1 }}
                variant="contained"
                color="error"
                size="small"
                startIcon={<DeleteIcon />}
                onClick={() => {
                  handleDeletion(e.id);
                }}
              >
                Delete
              </Button>
            </Paper>
          ))}
        </Box>
      )}
    </Box>
  );
};

const ManageExchangesPanel = (props: ManageExchangesPanelProps) => {
  const [exchangesProps, setExchangesProps] =
    useState<ActionExchangeTableProps | null>(null);
  const [message, setMessage] = useState<AlertMessage | null>(null);
  const [deletedExchangeId, setDeletedExchangeId] = useState<number | null>(
    null,
  );
  const [exchangesFetchStatus, setExchangesFetchStatus] =
    useState<DataFetchStatus>(exchangesProps != null ? "success" : "loading");

  const getExchanges = async () => {
    if (!props.userId) {
      return;
    }
    const result = await sendGetExchangesRequest(
      0,
      100,
      "endAt",
      null,
      null,
      null,
      null,
      null,
      null,
      Number(props.userId),
    );
    if (result.isSuccess) {
      const props: ActionExchangeTableProps = {
        message: message,
        setMessage: setMessage,
        setDeletedExchangeId: setDeletedExchangeId,
        exchanges: result.data.content.map((e) => ({
          id: e.id,
          timeRange: {
            startedAt: e.timeRange.startedAt,
            endAt: e.timeRange.endAt,
          },
          university: {
            id: e.university.id,
            name: e.university.englishName
              ? e.university.englishName
              : e.university.nativeName,
          },
          universityMajorName: e.fieldOfStudy.name,
          city: {
            name: e.university.city.name,
            countryName: e.university.city.country.englishName,
          },
        })),
      };
      setExchangesProps(props);
      setExchangesFetchStatus("success");
    } else {
      switch (result.error.status) {
        case "INTERNAL_SERVER_ERROR":
          setExchangesFetchStatus("serverError");
          break;
        case "SERVICE_UNAVAILABLE":
          setExchangesFetchStatus("connectionError");
          break;
      }
    }
  };

  useEffect(() => {
    if (!exchangesProps) {
      getExchanges();
    } else {
      setExchangesProps({
        exchanges: exchangesProps.exchanges.filter(
          (e) => e.id !== deletedExchangeId,
        ),
        message: message,
        setMessage: setMessage,
        setDeletedExchangeId: setDeletedExchangeId,
      });
    }
  }, [deletedExchangeId]);

  const getContent = () => {
    switch (exchangesFetchStatus) {
      case "loading":
        return <LoadingContent title="Loading exchanges" />;
      case "connectionError":
        return (
          <ContentLoadError
            title="Connection error"
            subheader="Failed to load exchanges"
          />
        );
      case "serverError":
        return (
          <ContentLoadError
            title="Server error"
            subheader="Failed to load exchanges"
          />
        );
      case "success":
        return exchangesProps && exchangesProps.exchanges.length !== 0 ? (
          <ActionExchangeTableProps {...exchangesProps} />
        ) : (
          <NoContent
            title="No exchanges yet"
            subheader="You haven't add any exchange."
          />
        );
    }
  };

  return (
    <Box>
      <PanelHeader label="Manage existing exchanges" />
      {getContent()}
    </Box>
  );
};

export default ManageExchangesPanel;
