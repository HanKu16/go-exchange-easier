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
import { PersonRemove } from "@mui/icons-material";
import { deepPurple } from "@mui/material/colors";
import Navbar from "../components/Navbar";
import { useEffect, useState } from "react";
import {
  sendGetFollowedUniversitiesRequest,
  sendGetFolloweesRequest,
} from "../utils/user";
import type { DataFetchStatus } from "../types/DataFetchStatus";
import { useNavigate } from "react-router-dom";
import {
  sendFollowUniversityRequest,
  sendFollowUserRequest,
  sendUnfollowUniversityRequest,
  sendUnfollowUserRequest,
} from "../utils/follow";
import { useSnackbar } from "../context/SnackBarContext";
import { useSignedInUser } from "../context/SignedInUserContext";
import { useApplicationState } from "../context/ApplicationStateContext";
import NoContent from "../components/NoContent";
import basicAvatar from "../assets/examples/basic-avatar.png";

type FollowBoxProps = {
  id: number;
  name: string;
  route: string;
  isFollowed: boolean;
  avatarUrl?: string;
  followEntity: FollowEntity;
  handleChange: (id: number) => void;
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

const FollowBox = (props: FollowBoxProps) => {
  const theme = useTheme();
  const isTinyScreen = useMediaQuery(theme.breakpoints.down("sm"));
  const navigate = useNavigate();
  const [isFollowed, setIsFollowed] = useState<boolean>(props.isFollowed);
  const { showAlert } = useSnackbar();

  const handleButtonClick = async () => {
    const wasFollowed = isFollowed;
    setIsFollowed((prevState) => !prevState);
    let isSuccess;
    if (props.followEntity === "user") {
      if (isFollowed) {
        isSuccess = (await sendUnfollowUserRequest(props.id)).isSuccess;
      } else {
        isSuccess = (await sendFollowUserRequest(props.id)).isSuccess;
      }
    } else if (props.followEntity === "university") {
      if (isFollowed) {
        isSuccess = (await sendUnfollowUniversityRequest(props.id)).isSuccess;
      } else {
        isSuccess = (await sendFollowUniversityRequest(props.id)).isSuccess;
      }
    }
    if (!isSuccess) {
      setIsFollowed(wasFollowed);
      showAlert(
        `Failed to ${wasFollowed ? "unfollow" : " follow back"}.`,
        "error",
      );
    } else {
      props.handleChange(props.id);
    }
  };

  const getAvatarUrl = () => {
    if (props.followEntity === "user") {
      return props.avatarUrl != null ? props.avatarUrl : basicAvatar;
    } else if (props.followEntity === "university") {
      return props.avatarUrl;
    }
  };

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
            src={getAvatarUrl()}
            sx={{
              bgcolor: deepPurple[500],
              width: { xs: 40, sm: 50, md: 56 },
              height: { xs: 40, sm: 50, md: 56 },
              fontSize: { xs: "0.9rem", sm: "1.25rem" },
              flexShrink: 0,
            }}
          >
            {props.name.charAt(0).toUpperCase()}
          </Avatar>
          <Box sx={{ minWidth: 0 }}>
            <Typography
              variant="h6"
              noWrap
              onClick={() => navigate(props.route)}
              sx={{
                fontWeight: "bold",
                fontSize: { xs: "0.95rem", sm: "1.1rem" },
                display: "block",
                cursor: "pointer",
                transition: "color 0.2s",
                "&:hover": {
                  color: "primary.main",
                  textDecoration: "underline",
                },
              }}
            >
              {props.name}
            </Typography>
          </Box>
        </Box>
        <Button
          variant="contained"
          size={isTinyScreen ? "small" : "medium"}
          endIcon={props.isFollowed ? <PersonRemove /> : <PersonAdd />}
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
          onClick={handleButtonClick}
        >
          {isFollowed ? "Unfollow" : "Follow"}
        </Button>
      </Stack>
    </Card>
  );
};

type UserFollow = {
  id: number;
  nick: string;
  avatarUrl?: string;
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
  const { signedInUser } = useSignedInUser();
  const { appState, setAppState } = useApplicationState();

  const getFollowees = async () => {
    const result = await sendGetFolloweesRequest(signedInUser.id);
    if (result.isSuccess) {
      setUserFollows(
        result.data.content.map((u) => ({
          id: u.id,
          nick: u.nick,
          avatarUrl: u.avatarUrl,
          isFollowed: true,
        })),
      );
      setUserFollowsFetchStatus("success");
    } else {
      switch (result.error.status) {
        case "INTERNAL_SERVER_ERROR":
          setUserFollowsFetchStatus("serverError");
          break;
        case "SERVICE_UNAVAILABLE":
          setUserFollowsFetchStatus("connectionError");
          break;
      }
    }
  };

  const getFollowedUniversities = async () => {
    const result = await sendGetFollowedUniversitiesRequest(signedInUser.id);
    if (result.isSuccess) {
      setUniversityFollows(
        result.data.content.map((u) => ({
          id: u.id,
          name: u.englishName || u.nativeName,
          countryName: u.city.country.englishName,
          isFollowed: true,
        })),
      );
      setUniversityFollowsFetchStatus("success");
    } else {
      switch (result.error.status) {
        case "INTERNAL_SERVER_ERROR":
          setUniversityFollowsFetchStatus("serverError");
          break;
        case "SERVICE_UNAVAILABLE":
          setUniversityFollowsFetchStatus("connectionError");
          break;
      }
    }
  };

  useEffect(() => {
    if (
      universityFollowsFetchStatus === "success" &&
      userFollowsFetchStatus === "success"
    ) {
      if (appState !== "success") {
        setAppState("success");
      }
    } else if (
      universityFollowsFetchStatus === "connectionError" ||
      userFollowsFetchStatus === "connectionError"
    ) {
      setAppState("connectionError");
    } else if (
      universityFollowsFetchStatus === "serverError" ||
      userFollowsFetchStatus === "serverError"
    ) {
      setAppState("serverError");
    }
  }, [universityFollowsFetchStatus, userFollowsFetchStatus]);

  useEffect(() => {
    setAppState("loading");
    getFollowees();
    getFollowedUniversities();
  }, []);

  const getFollowBoxes = () => {
    if (currentFollowEntity === "user") {
      return userFollows.map((u) => {
        const route = `/users/${u.id}`;
        return (
          <Box key={u.id} sx={{ display: "flex", justifyContent: "center" }}>
            <FollowBox
              id={u.id}
              name={u.nick}
              route={route}
              avatarUrl={u.avatarUrl}
              isFollowed={u.isFollowed}
              followEntity="user"
              handleChange={(id: number) => {
                setUserFollows((prevFollows) =>
                  prevFollows.map((f) =>
                    f.id === id ? { ...f, isFollowed: !f.isFollowed } : f,
                  ),
                );
              }}
            />
          </Box>
        );
      });
    } else if (currentFollowEntity === "university") {
      return universityFollows.map((u) => {
        const route = `/universities/${u.id}`;
        return (
          <Box key={u.id} sx={{ display: "flex", justifyContent: "center" }}>
            <FollowBox
              id={u.id}
              name={u.name}
              route={route}
              isFollowed={u.isFollowed}
              followEntity="university"
              avatarUrl={`/flags/${u.countryName}.png`}
              handleChange={(id: number) => {
                setUniversityFollows((prevFollows) =>
                  prevFollows.map((f) =>
                    f.id === id ? { ...f, isFollowed: !f.isFollowed } : f,
                  ),
                );
              }}
            />
          </Box>
        );
      });
    }
  };

  const getContent = () => {
    if (currentFollowEntity === "university") {
      return universityFollows.length !== 0 ? (
        getFollowBoxes()
      ) : (
        <NoContent
          title={"No follows"}
          subheader={"You don't have any follows on universities."}
        />
      );
    } else if (currentFollowEntity === "user") {
      return userFollows.length !== 0 ? (
        getFollowBoxes()
      ) : (
        <NoContent
          title={"No follows"}
          subheader={"You don't have any follows on users."}
        />
      );
    }
    return <></>;
  };

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
              currentFollowEntity === "user" && userFollows.length !== 0
                ? { xs: "minmax(0, 1fr)", xl: "repeat(3, minmax(0, 1fr))" }
                : { xs: "minmax(0, 1fr)" },
          }}
        >
          {getContent()}
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
