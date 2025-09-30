import { Typography } from "@mui/material"

const PanelHeader = ({label}: {label: string}) => {
  return (
    <Typography variant='h5' sx={{fontWeight: 600, fontSize: {xs: '1.25rem', md: '1.75rem'},
      color: 'text.primary', letterSpacing: 0.3, marginBottom: 1}}>
      {label}
    </Typography>
  )
}

export default PanelHeader