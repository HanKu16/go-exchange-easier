import React, { useState, createContext, useContext, type ReactNode } from 'react';
import { Snackbar, Alert, type AlertProps, type SnackbarCloseReason } from '@mui/material';

type SnackbarContextType = {
  showAlert: (message: string, severity?: AlertProps['severity']) => void;
}

const SnackbarContext = createContext<SnackbarContextType | undefined>(undefined);

type SnackbarProviderProps = {
  children: ReactNode;
}

export const SnackbarProvider: React.FC<SnackbarProviderProps> = ({children}) => {
  const [openAlert, setOpenAlert] = useState(false);
  const [message, setMessage] = useState('');
  const [severity, setSeverity] = useState<AlertProps['severity']>('info');

  const showAlert = (msg: string, sev: AlertProps['severity'] = 'info') => {
    setMessage(msg);
    setSeverity(sev);
    setOpenAlert(true);
  };

  const handleClose = (event: React.SyntheticEvent<any, Event> | Event,
    reason?: SnackbarCloseReason) => {
    if (reason === 'clickaway') return;
      setOpenAlert(false);
  };

  const contextValue: SnackbarContextType = { showAlert };

  return (
    <SnackbarContext.Provider value={contextValue}>
      {children}
      <Snackbar open={openAlert} autoHideDuration={6000}
        onClose={handleClose} anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
        <Alert severity={severity} sx={{width: '100%'}} onClose={handleClose}>
          {message || 'An error occurred. Please try again later.'}
        </Alert>
      </Snackbar>
    </SnackbarContext.Provider>
  );
};

export const useSnackbar = (): SnackbarContextType => {
  const context = useContext(SnackbarContext);
  if (!context) {
    throw new Error('useSnackbar must be used within a SnackbarProvider');
  }
  return context;
};
