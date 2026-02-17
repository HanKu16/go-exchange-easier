import { Paper, Tabs, Tab } from "@mui/material";

const SearchEntitiesOptions = (props: any) => (
  <Paper
    elevation={0}
    sx={{
      p: "4px",
      mb: 3,
      bgcolor: "#f3f4f6",
      borderRadius: "12px",
      display: "inline-flex",
    }}
  >
    <Tabs
      value={props.currentSearchEntity}
      onChange={(_, val) => {
        props.setCurrentSearchEntity(val);
      }}
      TabIndicatorProps={{ style: { display: "none" } }}
    >
      <Tab label="People" value="user" disableRipple sx={tabStyles} />
      <Tab
        label="Universities"
        value="university"
        disableRipple
        sx={tabStyles}
      />
    </Tabs>
  </Paper>
);

const tabStyles = {
  textTransform: "none",
  fontWeight: 600,
  fontSize: "0.95rem",
  color: "text.secondary",
  borderRadius: "8px",
  minHeight: "40px",
  px: 3,
  "&.Mui-selected": {
    color: "text.primary",
    backgroundColor: "#fff",
    boxShadow: "0px 2px 4px rgba(0,0,0,0.05)",
  },
} as const;

export default SearchEntitiesOptions;
