import React, {
  createContext,
  useContext,
  useState,
  type ReactNode,
} from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Button,
  Box,
} from "@mui/material";
import WarningRoundedIcon from "@mui/icons-material/WarningRounded";

interface ConfirmationOptions {
  title: string;
  message: string;
  confirmText?: string;
  cancelText?: string;
  confirmColor?: "error" | "primary" | "success" | "warning";
  onConfirm: () => void | Promise<void>;
}

interface ConfirmationDialogContextType {
  showConfirmation: (options: ConfirmationOptions) => void;
}

const ConfirmationDialogContext = createContext<
  ConfirmationDialogContextType | undefined
>(undefined);

export const ConfirmationDialogProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [open, setOpen] = useState(false);
  const [options, setOptions] = useState<ConfirmationOptions | null>(null);

  const showConfirmation = (config: ConfirmationOptions) => {
    setOptions(config);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleConfirm = async () => {
    if (options?.onConfirm) {
      await options.onConfirm();
    }
    handleClose();
  };

  return (
    <ConfirmationDialogContext.Provider value={{ showConfirmation }}>
      {children}

      <Dialog
        open={open}
        onClose={handleClose}
        PaperProps={{
          sx: { borderRadius: 4, p: 1, maxWidth: 400, textAlign: "center" },
        }}
      >
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            pt: 3,
            px: 3,
          }}
        >
          <WarningRoundedIcon
            color={options?.confirmColor === "error" ? "error" : "warning"}
            sx={{ fontSize: 48, mb: 1 }}
          />
          <DialogTitle sx={{ fontWeight: 800, p: 0, mb: 1 }}>
            {options?.title}
          </DialogTitle>
          <DialogContent sx={{ p: 0 }}>
            <DialogContentText
              sx={{ color: "text.secondary", fontSize: "0.95rem" }}
            >
              {options?.message}
            </DialogContentText>
          </DialogContent>
        </Box>

        <DialogActions sx={{ p: 3, gap: 1.5 }}>
          <Button
            fullWidth
            onClick={handleClose}
            variant="outlined"
            sx={{
              borderRadius: "12px",
              py: 1,
              color: "text.primary",
              borderColor: "#e2e8f0",
            }}
          >
            {options?.cancelText || "Cancel"}
          </Button>
          <Button
            fullWidth
            onClick={handleConfirm}
            variant="contained"
            color={options?.confirmColor || "primary"}
            disableElevation
            sx={{ borderRadius: "12px", py: 1, fontWeight: 700 }}
          >
            {options?.confirmText || "Confirm"}
          </Button>
        </DialogActions>
      </Dialog>
    </ConfirmationDialogContext.Provider>
  );
};

export const useConfirmation = () => {
  const context = useContext(ConfirmationDialogContext);
  if (!context)
    throw new Error(
      "useConfirmation must be used within ConfirmationDialogProvider",
    );
  return context;
};
