import {
  Box,
  Button,
  Typography,
  Avatar,
  Card,
  Stack,
  Container,
  Paper,
  Tab,
  Tabs,
  useTheme,
  useMediaQuery,
} from "@mui/material";
import { PersonAdd } from "@mui/icons-material";
import { deepPurple } from "@mui/material/colors";
import Navbar from "../components/Navbar";
import { useEffect, useState } from "react";
import {
  sendGetFollowedUniversitiesRequest,
  sendGetFolloweesRequest,
} from "../utils/user";
import type { DataFetchStatus } from "../types/DataFetchStatus";

type FollowBoxProps = {
  userId: number;
  nick: string;
};

type FollowEntity = "user" | "university";

type FollowEntitiesOptionsProps = {
  currentFollowEntity: FollowEntity;
  setCurrentFollowEntity: (entity: FollowEntity) => void;
};

const FollowEntitiesOptions = (props: FollowEntitiesOptionsProps) => (
  <Paper
    elevation={0}
    sx={{
      p: "4px",
      mb: 1,
      bgcolor: "#f3f4f6",
      borderRadius: "12px",
      display: "inline-flex",
    }}
  >
    <Tabs
      value={props.currentFollowEntity}
      onChange={(_, val) => {
        props.setCurrentFollowEntity(val);
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

const FollowBox = ({ userId, nick }: FollowBoxProps) => {
  const theme = useTheme();
  const isTinyScreen = useMediaQuery(theme.breakpoints.down("sm"));

  return (
    <Card
      elevation={0}
      sx={{
        width: "100%",
        maxWidth: "100%",
        p: { xs: 1.5, sm: 2, md: 3 },
        border: "1px solid",
        borderColor: "divider",
        borderRadius: 3,
        transition: "all 0.3s ease",
        "&:hover": {
          borderColor: "primary.main",
          transform: "translateY(-2px)",
          boxShadow: "0 6px 20px rgba(0,0,0,0.08)",
        },
      }}
    >
      <Stack
        direction="row"
        alignItems="center"
        justifyContent="space-between"
        spacing={{ xs: 1, sm: 2 }}
        sx={{ width: "100%", maxWidth: "100%", overflow: "hidden" }}
      >
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            gap: { xs: 1.5, sm: 2 },
            flex: 1,
            minWidth: 0,
          }}
        >
          <Avatar
            sx={{
              bgcolor: deepPurple[500],
              width: { xs: 40, sm: 50, md: 56 },
              height: { xs: 40, sm: 50, md: 56 },
              fontSize: { xs: "0.9rem", sm: "1.25rem" },
              flexShrink: 0,
            }}
          >
            {nick.charAt(0).toUpperCase()}
          </Avatar>
          <Box sx={{ minWidth: 0 }}>
            <Typography
              variant="h6"
              noWrap
              title={nick}
              sx={{
                fontWeight: "bold",
                fontSize: { xs: "0.95rem", sm: "1.1rem", md: "1.25rem" },
                lineHeight: 1.2,
                display: "block",
              }}
            >
              {nick}
            </Typography>

            <Typography
              variant="body2"
              color="text.secondary"
              noWrap
              sx={{
                fontSize: { xs: "0.75rem", sm: "0.85rem" },
                mt: 0.5,
                display: "block",
              }}
            >
              ID: {userId}
            </Typography>
          </Box>
        </Box>
        <Button
          variant="contained"
          size={isTinyScreen ? "small" : "medium"}
          endIcon={<PersonAdd />}
          sx={{
            borderRadius: 20,
            textTransform: "none",
            whiteSpace: "nowrap",
            minWidth: "auto",
            px: { xs: 2, sm: 3 },
            fontSize: { xs: "0.8rem", sm: "0.9rem" },
            boxShadow: "none",
            flexShrink: 0,
            marginLeft: 1,
          }}
        >
          Follow
        </Button>
      </Stack>
    </Card>
  );
};

type UserFollow = {
  id: number;
  nick: string;
  isFollowed: boolean;
};

type UniversityFollow = {
  id: number;
  name: string;
  countryName: string;
  isFollowed: boolean;
};

const FollowPage = () => {
  const [currentFollowEntity, setCurrentFollowEntity] =
    useState<FollowEntity>("user");
  const [userFollows, setUserFollows] = useState<UserFollow[]>([]);
  const [universityFollows, setUniversityFollows] = useState<
    UniversityFollow[]
  >([]);
  const [userFollowsFetchStatus, setUserFollowsFetchStatus] =
    useState<DataFetchStatus>("loading");
  const [universityFollowsFetchStatus, setUniversityFollowsFetchStatus] =
    useState<DataFetchStatus>("loading");

  const getFollowees = async () => {
    const result = await sendGetFolloweesRequest();
    if (result.isSuccess) {
      setUserFollows(
        result.data.content.map((u) => ({
          id: u.id,
          nick: u.nick,
          isFollowed: true,
        }))
      );
      setUniversityFollowsFetchStatus("success");
    } else {
    }
  };

  const getFollowedUniversities = async () => {
    const result = await sendGetFollowedUniversitiesRequest();
    if (result.isSuccess) {
      setUniversityFollows(
        result.data.content.map((u) => ({
          id: u.id,
          name: u.englishName || u.nativeName,
          countryName: u.city.country.englishName,
          isFollowed: true,
        }))
      );
      setUniversityFollowsFetchStatus("success");
    } else {
    }
  };

  // Inside FollowPage component
  const getFollowBoxes = () => {
    if (currentFollowEntity === "user") {
      return userFollows.map((user) => (
        <Box key={user.id} sx={{ display: "flex", justifyContent: "center" }}>
          <FollowBox userId={user.id} nick={user.nick} />
        </Box>
      ));
    } else if (currentFollowEntity === "university") {
      return universityFollows.map((university) => (
        <Box
          key={university.id}
          sx={{ display: "flex", justifyContent: "center" }}
        >
          <FollowBox
            userId={university.id}
            // CHANGE HERE: Pass the full name.
            // Do not use substring() or manual logic.
            nick={university.name}
          />
        </Box>
      ));
    }
  };

  useEffect(() => {
    getFollowees();
    getFollowedUniversities();
  }, []);

  return (
    <Box sx={{ display: "flex", minHeight: "100vh", flexDirection: "column" }}>
      <Navbar />

      <Container
        sx={{
          paddingY: 4,
          maxWidth: "lg",
          flexGrow: 1,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <FollowEntitiesOptions
          currentFollowEntity={currentFollowEntity}
          setCurrentFollowEntity={setCurrentFollowEntity}
        />
        <Box
          sx={{
            display: "grid",
            width: "100%",
            marginTop: 4,
            gap: 2,
            gridTemplateColumns:
              currentFollowEntity === "user"
                ? { xs: "minmax(0, 1fr)", xl: "repeat(3, minmax(0, 1fr))" }
                : { xs: "minmax(0, 1fr)" },
          }}
        >
          {getFollowBoxes()}
        </Box>
      </Container>
    </Box>
  );
};

export default FollowPage;

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
