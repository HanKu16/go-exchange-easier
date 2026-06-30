import { useState } from 'react';
import { 
  Dialog, DialogTitle, DialogContent, DialogContentText, 
  TextField, DialogActions, Button, Box, CircularProgress 
} from "@mui/material";

export type ReportDialogProps = {
  open: boolean;
  onClose: () => void;
  onConfirm: (description: string | null) => Promise<boolean>;
  title: string;
  descriptionText: string;
  maxReportDescriptionSize: number;
}

export const ReportDialog = ({ open, onClose, onConfirm, title, descriptionText, maxReportDescriptionSize}: ReportDialogProps) => {
  const [reportDescription, setReportDescription] = useState("");
  const [isReporting, setIsReporting] = useState(false);

  const handleConfirm = async () => {
    setIsReporting(true);
    const success = await onConfirm(
      reportDescription.trim().length > 0 ? reportDescription.trim() : null
    );
    if (success) {
      setReportDescription("");
      onClose();
    }
    setIsReporting(false);
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm" disableScrollLock PaperProps={{ sx: { borderRadius: 4 } }}>
      <DialogTitle sx={{ fontWeight: 800, color: "#04315f" }}>{title}</DialogTitle>
      <DialogContent sx={{ pt: 1 }}>
        <DialogContentText sx={{ color: "#04315f", mb: 2 }}>{descriptionText}</DialogContentText>
        <TextField
          autoFocus fullWidth label="Description" placeholder="Optional details..." multiline minRows={4}
          value={reportDescription} disabled={isReporting}
          error={reportDescription.length > maxReportDescriptionSize}
          helperText={`${reportDescription.length}/${maxReportDescriptionSize} characters`}
          onChange={(e) => setReportDescription(e.target.value)}
          sx={{
            mt: 0.5,
            "& .MuiOutlinedInput-root": { "& fieldset": { borderColor: "#04315f" }, "&:hover fieldset": { borderColor: "#04315f" }, "&.Mui-focused fieldset": { borderColor: "#04315f" } },
            "& .MuiInputLabel-root": { color: "#04315f" },
            "& .MuiInputLabel-root.Mui-focused": { color: "#04315f" },
          }}
        />
      </DialogContent>
      <DialogActions sx={{ p: 3, gap: 1.5 }}>
        <Button fullWidth onClick={onClose} variant="outlined" disabled={isReporting} sx={{ borderRadius: "12px", py: 1, borderColor: "#04315f", color: "#04315f" }}>
          Cancel
        </Button>
        <Button fullWidth onClick={handleConfirm} variant="contained" disableElevation disabled={isReporting || reportDescription.length > maxReportDescriptionSize} sx={{ borderRadius: "12px", py: 1, fontWeight: 700, backgroundColor: "#04315f" }}>
          {isReporting ? <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}><CircularProgress color="inherit" size={18} /> Reporting...</Box> : "Report"}
        </Button>
      </DialogActions>
    </Dialog>
  );
};