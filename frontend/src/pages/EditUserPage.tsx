import Navbar from "../components/Navbar";
import { useState } from "react";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Typography from "@mui/material/Typography";
import PersonIcon from "@mui/icons-material/Person";
import type { ReactElement } from "react";
import { Collapse } from "@mui/material";
import { ExpandLess, ExpandMore } from "@mui/icons-material";
import { Fragment } from "react";
import PublicIcon from "@mui/icons-material/Public";
import AssignHomeUniversityPanel from "../panels/AssignHomeUniversityPanel";
import UpdateUserDescriptionPanel from "../panels/UpdateUserDescriptionPanel";
import UpdateUserStatusPanel from "../panels/UpdateUserStatusPanel";
import AssignCountryOfOriginPanel from "../panels/AssignCountryOfOriginPanel";
import AddExchangePanel from "../panels/AddExchangePanel";
import ManageExchangesPanel from "../panels/ManageExchangesPanel";
import { getSignedInUserId } from "../utils/user";

type SectionName = "Informations" | "Exchanges";

type SubsectionName =
  | "Home university"
  | "Country of origin"
  | "Status"
  | "Description"
  | "Add exchange"
  | "Manage exchanges";

type Section = {
  name: SectionName;
  subsections: SubsectionName[];
  icon: ReactElement<any, any>;
};

type SectionListProps = {
  selectedSectionName: SectionName | null;
  setSelectedSectionName: (value: SectionName | null) => void;
  selectedSubsectionName: SubsectionName | null;
  setSelectedSubsectionName: (value: SubsectionName | null) => void;
};

const SectionsList = (props: SectionListProps) => {
  const sections: Section[] = [
    {
      name: "Informations",
      subsections: [
        "Home university",
        "Country of origin",
        "Status",
        "Description",
      ],
      icon: <PersonIcon />,
    },
    {
      name: "Exchanges",
      subsections: ["Add exchange", "Manage exchanges"],
      icon: <PublicIcon />,
    },
  ];

  const handleSectionClick = (section: Section) => {
    if (props.selectedSectionName === section.name) {
      props.setSelectedSectionName(null);
    } else {
      props.setSelectedSectionName(section.name);
    }
  };

  const handleSubsectionClick = (subsection: SubsectionName) => {
    if (props.selectedSubsectionName === subsection) {
      props.setSelectedSubsectionName(null);
    } else {
      props.setSelectedSubsectionName(subsection);
    }
  };

  return (
    <Box
      sx={{
        width: { xs: "100vw", sm: "30%", lg: "20%" },
        backgroundColor: "#f9f9f9",
        borderRight: "1px solid #e0e0e0",
        display: "flex",
        flexDirection: "column",
      }}
    >
      <Typography
        variant="h6"
        sx={{
          padding: { xs: 1.5, sm: 2 },
          fontSize: { xs: "1rem", sm: "1.25rem" },
          color: "text.secondary",
          fontWeight: 600,
        }}
      >
        Edit
      </Typography>
      <List component="nav" sx={{ paddingTop: 0 }}>
        {sections.map((section) => (
          <Fragment key={section.name}>
            <ListItemButton
              selected={props.selectedSectionName === section.name}
              onClick={() => handleSectionClick(section)}
              sx={{
                minHeight: 48,
                "&.Mui-selected": {
                  backgroundColor: "rgba(0, 0, 0, 0.04)",
                  borderLeft: "4px solid #1976d2",
                  color: "#1976d2",
                },
                "&.Mui-selected .MuiListItemIcon-root": { color: "#1976d2" },
              }}
            >
              <ListItemIcon sx={{ minWidth: 40 }}>{section.icon}</ListItemIcon>
              <ListItemText
                primaryTypographyProps={{
                  fontSize: { xs: "0.9rem", sm: "1rem" },
                }}
                primary={section.name}
              />
              {props.selectedSectionName === section.name ? (
                <ExpandLess />
              ) : (
                <ExpandMore />
              )}
            </ListItemButton>
            <Collapse
              in={props.selectedSectionName === section.name}
              timeout="auto"
              unmountOnExit
            >
              <List component="div" disablePadding>
                {section.subsections.map((ss) => (
                  <ListItemButton
                    key={ss}
                    selected={props.selectedSubsectionName === ss}
                    onClick={() => handleSubsectionClick(ss)}
                    sx={{
                      pl: { xs: 3, sm: 4 },
                      minHeight: 44,
                      "&.Mui-selected": {
                        backgroundColor: "rgba(0, 0, 0, 0.04)",
                        borderLeft: "4px solid #207ddaff",
                        color: "#207ddaff",
                      },
                      "&.Mui-selected .MuiListItemIcon-root": {
                        color: "#207ddaff",
                      },
                    }}
                  >
                    <ListItemText
                      primaryTypographyProps={{
                        fontSize: { xs: "0.85rem", sm: "0.95rem" },
                      }}
                      primary={ss}
                    />
                  </ListItemButton>
                ))}
              </List>
            </Collapse>
          </Fragment>
        ))}
      </List>
    </Box>
  );
};

const EditUserPage = () => {
  const [selectedSectionName, setSelectedSectionName] =
    useState<SectionName | null>(null);
  const [selectedSubsectionPanel, setSelectedSubsectionName] =
    useState<SubsectionName | null>(null);
  const userId: string = getSignedInUserId();

  const getPanel = () => {
    if (selectedSubsectionPanel === "Country of origin") {
      return <AssignCountryOfOriginPanel />;
    } else if (selectedSubsectionPanel === "Status") {
      return <UpdateUserStatusPanel />;
    } else if (selectedSubsectionPanel === "Description") {
      return <UpdateUserDescriptionPanel />;
    } else if (selectedSubsectionPanel === "Home university") {
      return <AssignHomeUniversityPanel />;
    } else if (selectedSubsectionPanel === "Add exchange") {
      return <AddExchangePanel />;
    } else if (selectedSubsectionPanel === "Manage exchanges") {
      return <ManageExchangesPanel userId={userId} />;
    }
    return <></>;
  };

  return (
    <>
      <Box sx={{ display: "flex", height: "100vh", flexDirection: "column" }}>
        <Navbar />
        <Box
          sx={{
            display: "flex",
            flexGrow: { sm: 1 },
            flexDirection: { xs: "column", sm: "row" },
          }}
        >
          <SectionsList
            selectedSectionName={selectedSectionName}
            setSelectedSectionName={setSelectedSectionName}
            selectedSubsectionName={selectedSubsectionPanel}
            setSelectedSubsectionName={setSelectedSubsectionName}
          />
          <Box sx={{ flexGrow: 1, padding: 4 }}>{getPanel()}</Box>
        </Box>
      </Box>
    </>
  );
};

export default EditUserPage;
