import { useState, useEffect } from 'react';
import { 
  Dialog, DialogTitle, DialogContent, DialogContentText, 
  TextField, DialogActions, Button, Box, CircularProgress,
  MenuItem 
} from "@mui/material";
import { useSnackbar } from '../context/SnackBarContext';
import type { DictionaryEntry } from '../dtos/common/DictionaryEntry';
import { sendGetReportDictionaryRequest } from '../utils/api/report';
import type { DataFetchStatus } from '../types/DataFetchStatus'; 

export type ReportDialogProps = {
  open: boolean;
  onClose: () => void;
  onConfirm: (reason: string, description: string | null) => Promise<boolean>;
  title: string;
  descriptionText: string;
  maxReportDescriptionSize: number;
}

export const ReportDialog = ({ 
  open, 
  onClose, 
  onConfirm, 
  title, 
  descriptionText, 
  maxReportDescriptionSize
}: ReportDialogProps) => {
  const { showAlert } = useSnackbar();
  const [reportDescription, setReportDescription] = useState("");
  const [selectedReason, setSelectedReason] = useState("");
  const [isReporting, setIsReporting] = useState(false);
  const [reasons, setReasons] = useState<DictionaryEntry[]>([]);  
  const [dictionaryFetchStatus, setDictionaryFetchStatus] = useState<DataFetchStatus | "idle">("idle");

  useEffect(() => {
    const fetchDictionary = async () => {
      setDictionaryFetchStatus("loading");
      const result = await sendGetReportDictionaryRequest();
      
      if (result.isSuccess) {
        setReasons(result.data.reasons);
        setDictionaryFetchStatus("success");
      } else {
        switch (result.error.status) {
          case "INTERNAL_SERVER_ERROR":
            showAlert("Server error occurred. Could not load report reasons.", "error");
            break;
          case "SERVICE_UNAVAILABLE":
            showAlert("Service is currently unavailable. Please try again later.", "error");
            break;
          default:
            showAlert("Failed to load report reasons. Please try again later.", "error");
            break;
        }
        setDictionaryFetchStatus("idle");
        onClose();
      }
    };

    if (open) {
      fetchDictionary();
    } else {
      setReportDescription("");
      setSelectedReason("");
      setDictionaryFetchStatus("idle");
    }
  }, [open]); 

  const handleConfirm = async () => {
    setIsReporting(true);
    const isSuccess = await onConfirm(
      selectedReason,
      reportDescription.trim().length > 0 ? reportDescription.trim() : null
    );
    if (isSuccess) {
      setReportDescription("");
      setSelectedReason("");
      onClose();
    }
    setIsReporting(false);
  };

  const textFieldStyles = {
    "& .MuiOutlinedInput-root": { "& fieldset": { borderColor: "#04315f" }, "&:hover fieldset": { borderColor: "#04315f" }, "&.Mui-focused fieldset": { borderColor: "#04315f" } },
    "& .MuiInputLabel-root": { color: "#04315f" },
    "& .MuiInputLabel-root.Mui-focused": { color: "#04315f" },
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm" disableScrollLock PaperProps={{ sx: { borderRadius: 4 } }}>
      <DialogTitle sx={{ fontWeight: 800, color: "#04315f" }}>{title}</DialogTitle>
      
      {open && (
        <>
          <DialogContent sx={{ pt: 1 }}>
            <DialogContentText sx={{ color: "#04315f", mb: 2 }}>{descriptionText}</DialogContentText>
            {dictionaryFetchStatus === "loading" && (
              <Box sx={{ display: 'flex', justifyContent: 'center', p: 4 }}>
                <CircularProgress sx={{ color: "#04315f" }} />
              </Box>
            )}
            {dictionaryFetchStatus === "success" && (
              <>
                <TextField
                  select
                  fullWidth
                  label="Reason"
                  required
                  value={selectedReason}
                  disabled={isReporting}
                  onChange={(e) => setSelectedReason(e.target.value)}
                  sx={{ mb: 2, ...textFieldStyles }}
                >
                  {reasons.map((reason) => (
                    <MenuItem key={reason.value} value={reason.value}>
                      {reason.label}
                    </MenuItem>
                  ))}
                </TextField>
                <TextField
                  fullWidth 
                  label="Description" 
                  placeholder="Optional details..." 
                  multiline 
                  minRows={4}
                  value={reportDescription} 
                  disabled={isReporting}
                  error={reportDescription.length > maxReportDescriptionSize}
                  helperText={`${reportDescription.length}/${maxReportDescriptionSize} characters`}
                  onChange={(e) => setReportDescription(e.target.value)}
                  sx={{ mt: 0.5, ...textFieldStyles }}
                />
              </>
            )}
          </DialogContent>
          <DialogActions sx={{ p: 3, gap: 1.5 }}>
            <Button 
              fullWidth 
              onClick={onClose} 
              variant="outlined" 
              disabled={isReporting} 
              sx={{ borderRadius: "12px", py: 1, borderColor: "#04315f", color: "#04315f" }}
            >
              Cancel
            </Button>
            <Button 
              fullWidth 
              onClick={handleConfirm} 
              variant="contained" 
              disableElevation 
              disabled={isReporting || !selectedReason || reportDescription.length > maxReportDescriptionSize || dictionaryFetchStatus !== "success"} 
              sx={{ borderRadius: "12px", py: 1, fontWeight: 700, backgroundColor: "#04315f" }}
            >
              {isReporting ? (
                <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                  <CircularProgress color="inherit" size={18} /> Reporting...
                </Box>
              ) : "Report"}
            </Button>
          </DialogActions>
        </>
      )}
    </Dialog>
  );
};
