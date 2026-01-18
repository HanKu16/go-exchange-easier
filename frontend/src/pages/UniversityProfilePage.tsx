import { Box, Typography, Grid } from "@mui/material";
import { useTheme, useMediaQuery } from "@mui/material";
import { Button } from "@mui/material";
import { useEffect, useState } from "react";
import LanguageIcon from "@mui/icons-material/Language";
import { useParams } from "react-router-dom";
import Navbar from "../components/Navbar";
import PlaceIcon from "@mui/icons-material/Place";
import BookmarkIcon from "@mui/icons-material/Bookmark";
import {
  sendGetReviewsCountRequest,
  sendGetUniversityProfileRequest,
  sendGetUniversityReviewsRequest,
} from "../utils/university";
import { Container } from "@mui/material";
import Link from "@mui/material/Link";
import { Pagination } from "@mui/material";
import type { UniversityReviewProps } from "../components/UniversityReview";
import { getLocalDate } from "../utils/date-utils";
import NoContent from "../components/NoContent";
import UniversityReview from "../components/UniversityReview";
import {
  sendFollowUniversityRequest,
  sendUnfollowUniversityRequest,
} from "../utils/follow";
import { sendCreateReviewRequest } from "../utils/review";
import { TextField, Rating, Stack } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import StarIcon from "@mui/icons-material/Star";
import AddIcon from "@mui/icons-material/Add";
import { useSnackbar } from "../context/SnackBarContext";
import { type DataFetchStatus } from "../types/DataFetchStatus";
import { isInteger } from "../utils/number-utils";
import NotFoundPage from "./NotFoundPage";
import SearchOffIcon from "@mui/icons-material/SearchOff";
import type { GetUniversityProfileResponse } from "../dtos/university/GetUniversityProfileResponse";
import LoadingContent from "../components/LoadingContent";
import ContentLoadError from "../components/ContentLoadError";
import { useSignedInUser } from "../context/SignedInUserContext";
import { useApplicationState } from "../context/ApplicationStateContext";

type FollowButtonProps = {
  universityId: number | string | undefined;
  isFollowed: boolean;
  setIsFollowed: (value: boolean) => void;
};

const FollowButton = (props: FollowButtonProps) => {
  const { showAlert } = useSnackbar();

  const handleFollow = async () => {
    if (props.universityId) {
      props.setIsFollowed(true);
      const result = await sendFollowUniversityRequest(props.universityId);
      if (!result.isSuccess) {
        props.setIsFollowed(false);
        showAlert("An error occured. Try follow university later.", "error");
      }
    }
  };
  const handleUnfollow = async () => {
    if (props.universityId) {
      props.setIsFollowed(false);
      const result = await sendUnfollowUniversityRequest(props.universityId);
      if (!result.isSuccess) {
        props.setIsFollowed(true);
        showAlert("An error occured. Try unfollow university later.", "error");
      }
    }
  };

  return (
    <Container
      sx={{
        display: "flex",
        justifyContent: { lg: "center" },
        marginTop: { xs: 2, lg: 4 },
      }}
    >
      {props.isFollowed ? (
        <Button
          variant="outlined"
          size="medium"
          endIcon={<BookmarkIcon />}
          onClick={() => handleUnfollow()}
        >
          UNFOLLOW
        </Button>
      ) : (
        <Button
          variant="contained"
          size="medium"
          endIcon={<BookmarkIcon />}
          onClick={() => handleFollow()}
        >
          FOLLOW
        </Button>
      )}
    </Container>
  );
};

type GoToSumbitReviewButtonProps = {
  handleClick: () => void;
};

const ShowReviewInputButton = (props: GoToSumbitReviewButtonProps) => {
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "flex-start",
        width: "91%",
        margin: "auto",
        marginBottom: 2,
      }}
    >
      <Button
        variant="contained"
        startIcon={<AddIcon />}
        onClick={props.handleClick}
        sx={{
          borderRadius: 2,
          textTransform: "none",
          fontWeight: "bold",
          px: 3,
          py: 1,
          backgroundColor: "#04315f",
          "&:hover": {
            backgroundColor: "#064080",
          },
        }}
      >
        Create review
      </Button>
      <br />
    </Box>
  );
};

type ReviewInput = {
  handleGoBackClick: () => void;
  universityId: number | string | undefined;
  handleSuccessfulCreation: (reviewProps: UniversityReviewProps) => void;
};

const ReviewInput = (props: ReviewInput) => {
  const [postText, setPostText] = useState<string>("");
  const [ratingValue, setRatingValue] = useState<number | null>(null);
  const isSubmitDisabled =
    !postText.trim() || ratingValue === null || ratingValue === 0;
  const { showAlert } = useSnackbar();

  const handleSubmit = async () => {
    if (isSubmitDisabled || !props.universityId) {
      showAlert("Failed to create review. Please try again later.", "error");
      return;
    }
    const result = await sendCreateReviewRequest({
      text: postText,
      starRating: ratingValue,
      universityId: Number(props.universityId),
    });
    if (result.isSuccess) {
      const reviewProps: UniversityReviewProps = {
        id: result.data.id,
        title: result.data.author.nick,
        subheader: getLocalDate(result.data.createdAt),
        starRating: result.data.starRating,
        textContent: result.data.textContent,
        reactions: result.data.reactions,
      };
      props.handleSuccessfulCreation(reviewProps);
      props.handleGoBackClick();
      setPostText("");
      setRatingValue(null);
      showAlert("Review was created successfully.", "success");
    } else {
      showAlert("Failed to create review. Please try again later.", "error");
    }
  };

  return (
    <Box
      sx={{
        width: "91%",
        margin: "auto",
        p: 3,
        boxShadow: 3,
        borderRadius: 2,
        background: "white",
        marginBottom: 4,
      }}
    >
      <Box sx={{ display: "flex", justifyContent: "space-between" }}>
        <Typography variant="h5" gutterBottom>
          Submit your review
        </Typography>
        <Button
          variant="contained"
          onClick={props.handleGoBackClick}
          sx={{
            borderRadius: 2,
            textTransform: "none",
            fontWeight: "bold",
            px: 3,
            py: 1,
            backgroundColor: "#04315f",
            "&:hover": {
              backgroundColor: "#064080",
            },
          }}
        >
          Go back
        </Button>
      </Box>
      <Stack direction="row" spacing={2} alignItems="center" sx={{ my: 2 }}>
        <Typography variant="body1">Your Rating:</Typography>
        <Rating
          name="post-rating"
          value={ratingValue}
          precision={1}
          onChange={(_, newValue) => setRatingValue(newValue)}
          emptyIcon={<StarIcon style={{ opacity: 0.55 }} fontSize="inherit" />}
        />
        {ratingValue !== null && ratingValue > 0 && (
          <Typography variant="body2" color="text.secondary">
            ({ratingValue} out of 5)
          </Typography>
        )}
      </Stack>
      <TextField
        label="Text"
        placeholder="What do you think?"
        multiline
        rows={4}
        fullWidth
        variant="outlined"
        value={postText}
        onChange={(e) => setPostText(e.target.value)}
        sx={{ mb: 3 }}
      />
      <Button
        variant="contained"
        endIcon={<SendIcon />}
        onClick={handleSubmit}
        disabled={isSubmitDisabled}
        fullWidth
        size="large"
      >
        Submit
      </Button>
    </Box>
  );
};

type UniversityDataPanelProps = {
  userId: number | string;
  university: {
    id: number | string;
    nativeName: string;
    englishName: string | null;
    linkToWebsite: string | null;
    cityName: string;
    countryName: string;
  };
  isFollowed: boolean;
};

const UniversityDataPanel = (props: UniversityDataPanelProps) => {
  const theme = useTheme();
  const isLgScreen = useMediaQuery(theme.breakpoints.up("lg"));
  const [isFollowed, setIsFollowed] = useState<boolean>(props.isFollowed);

  return (
    <>
      {isLgScreen ? (
        <Container
          sx={{
            backgroundColor: "#182c44",
            minHeight: "100vh",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            paddingY: 8,
            position: "fixed",
            width: "inherit",
          }}
        >
          <img
            alt="Country flag"
            src={`/flags/${props.university.countryName}.png`}
            style={{ height: "8rem", marginBottom: 8 }}
          />
          <Typography
            sx={{
              color: "white",
              fontSize: "1.7rem",
              fontWeight: "700",
              paddingTop: 2,
              textAlign: "center",
            }}
          >
            {props.university.nativeName}
          </Typography>
          {props.university.englishName && (
            <Typography
              sx={{
                color: "#e6e2e2ff",
                fontSize: "1.1rem",
                fontWeight: "700",
                textAlign: "center",
                marginTop: 1,
              }}
            >
              {props.university.englishName}
            </Typography>
          )}
          <Container sx={{ width: "100%", paddingTop: 3 }}>
            <Box sx={{ display: "flex" }}>
              <PlaceIcon sx={{ color: "white", fontSize: "2rem" }} />
              <Typography
                sx={{
                  color: "white",
                  fontSize: "1.5rem",
                  fontWeight: "600",
                  marginLeft: 1,
                }}
              >
                {props.university.cityName}, {props.university.countryName}
              </Typography>
            </Box>
            {props.university.linkToWebsite && (
              <Box sx={{ display: "flex", marginTop: 1, marginLeft: 0.5 }}>
                <LanguageIcon sx={{ color: "white", fontSize: "1.7rem" }} />
                <Link
                  href={props.university.linkToWebsite}
                  underline="hover"
                  color="primary"
                  sx={{
                    fontWeight: 500,
                    fontSize: "1.2rem",
                    marginLeft: 1.5,
                    "&:hover": { color: "secondary.main" },
                  }}
                >
                  Go to website
                </Link>
              </Box>
            )}
            <FollowButton
              universityId={props.university.id}
              isFollowed={isFollowed}
              setIsFollowed={setIsFollowed}
            />
          </Container>
        </Container>
      ) : (
        <Box
          sx={{
            backgroundColor: "#182c44",
            display: "flex",
            flexDirection: "column",
            paddingY: 2,
          }}
        >
          <Container
            sx={{ marginTop: 2, display: "flex", flexDirection: "row" }}
          >
            <Box>
              <img
                alt="Country flag"
                src={`/flags/${props.university.countryName}.png`}
                style={{ height: "3.5rem", marginBottom: 8 }}
              />
            </Box>
            <Container sx={{ display: "flex", flexDirection: "column" }}>
              <Typography
                sx={{
                  color: "white",
                  fontSize: "1rem",
                  fontWeight: "700",
                  paddingY: 0.4,
                  marginLeft: 0.5,
                  marginBottom: 1,
                }}
              >
                {props.university.nativeName}
              </Typography>
              <Box>
                <Box sx={{ display: "flex" }}>
                  <PlaceIcon sx={{ color: "white", fontSize: "1.3rem" }} />
                  <Typography
                    sx={{
                      color: "white",
                      fontSize: "1rem",
                      fontWeight: "500",
                      marginLeft: 1,
                    }}
                  >
                    {props.university.cityName}, {props.university.countryName}
                  </Typography>
                </Box>
                {props.university.linkToWebsite && (
                  <Box sx={{ display: "flex", marginTop: 1, marginLeft: 0.3 }}>
                    <LanguageIcon
                      sx={{ color: "white", fontSize: "1rem", marginTop: 0.5 }}
                    />
                    <Link
                      href={props.university.linkToWebsite}
                      underline="hover"
                      color="primary"
                      sx={{
                        fontWeight: 500,
                        fontSize: "1.1rem",
                        marginLeft: 1,
                        "&:hover": { color: "secondary.main" },
                      }}
                    >
                      Go to website
                    </Link>
                  </Box>
                )}
              </Box>
              <FollowButton
                universityId={props.university.id}
                isFollowed={isFollowed}
                setIsFollowed={setIsFollowed}
              />
            </Container>
          </Container>
        </Box>
      )}
    </>
  );
};

type FeedPanelProps = {
  universityId: number | string;
};

const FeedPanel = (props: FeedPanelProps) => {
  const [reviewsFetchStatus, setReviewsFetchStatus] =
    useState<DataFetchStatus>("loading");
  const [showReviewInput, setShowReviewInput] = useState<boolean>(false);
  const [reviewProps, setReviewsProps] = useState<UniversityReviewProps[]>([]);
  const [currentPageNumber, setCurrentPageNumber] = useState<number>(1);
  const [totalPagesCount, setTotalPagesCount] = useState<number>(0);
  const pageSize = 5;

  const handleShowingSubmitReviewSection = () =>
    setShowReviewInput((prevValue) => !prevValue);

  const getReviews = async (pageNumber: number) => {
    const result = await sendGetUniversityReviewsRequest(
      props.universityId,
      pageNumber,
      pageSize,
    );
    if (result.isSuccess) {
      const props: UniversityReviewProps[] = result.data.content.map((r) => ({
        id: r.id,
        title: r.author.nick,
        subheader: getLocalDate(r.createdAt),
        starRating: r.starRating,
        textContent: r.textContent,
        reactions: r.reactions,
      }));
      setReviewsProps(props);
      setReviewsFetchStatus("success");
    } else {
      switch (result.error.status) {
        case "INTERNAL_SERVER_ERROR":
          setReviewsFetchStatus("serverError");
          break;
        case "SERVICE_UNAVAILABLE":
          setReviewsFetchStatus("connectionError");
          break;
      }
    }
  };

  const getTotalPagesCount = async () => {
    const result = await sendGetReviewsCountRequest(props.universityId);
    if (result.isSuccess) {
      setTotalPagesCount(Math.ceil(result.data.count / pageSize));
    } else {
      switch (result.error.status) {
        case "INTERNAL_SERVER_ERROR":
          setReviewsFetchStatus("serverError");
          break;
        case "SERVICE_UNAVAILABLE":
          setReviewsFetchStatus("connectionError");
          break;
      }
    }
  };

  const getReviewsContent = () => {
    if (reviewsFetchStatus === "success") {
      if (reviewProps.length !== 0) {
        return (
          <Container
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              gap: 3,
            }}
          >
            {reviewProps.map((rp) => (
              <UniversityReview {...rp} key={rp.id} />
            ))}
          </Container>
        );
      } else {
        return (
          <NoContent
            title={"No reviews yet"}
            subheader={"There are no reviews about this university so far"}
          />
        );
      }
    } else if (reviewsFetchStatus === "loading") {
      return <LoadingContent title="Loading reviews" />;
    } else if (reviewsFetchStatus === "connectionError") {
      return (
        <ContentLoadError
          title="Connection error"
          subheader="An error occurred while fetching reviews."
        />
      );
    } else if (reviewsFetchStatus === "serverError") {
      return (
        <ContentLoadError
          title="Server error"
          subheader="An error occurred while fetching reviews."
        />
      );
    }
  };

  useEffect(() => {
    getTotalPagesCount();
  }, []);

  useEffect(() => {
    setReviewsFetchStatus("loading");
    getReviews(currentPageNumber - 1);
  }, [currentPageNumber]);

  return (
    <Box
      sx={{
        paddingBottom: 4,
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        minHeight: "95vh",
      }}
    >
      <Box>
        <Typography
          sx={{
            fontSize: { xs: "1.3rem", lg: "1.7rem" },
            fontWeight: 600,
            paddingY: 2.5,
            paddingLeft: { xs: 2, lg: 4 },
          }}
        >
          University reviews
        </Typography>
        {showReviewInput ? (
          <ReviewInput
            handleGoBackClick={handleShowingSubmitReviewSection}
            handleSuccessfulCreation={(
              createdReviewProps: UniversityReviewProps,
            ) => setReviewsProps([createdReviewProps, ...reviewProps])}
            universityId={props.universityId}
          />
        ) : (
          <ShowReviewInputButton
            handleClick={handleShowingSubmitReviewSection}
          />
        )}
        {getReviewsContent()}
      </Box>
      {totalPagesCount > 1 && (
        <Container
          sx={{ display: "flex", justifyContent: "center", marginTop: 3 }}
        >
          <Pagination
            count={totalPagesCount}
            showFirstButton
            showLastButton
            onChange={(_, value) => setCurrentPageNumber(value)}
          />
        </Container>
      )}
    </Box>
  );
};

type UniversityProfileFetchStatus = DataFetchStatus | "universityNotFound";

const UniversityProfilePage = () => {
  const theme = useTheme();
  const isLgScreen = useMediaQuery(theme.breakpoints.up("lg"));
  const { universityId } = useParams();
  const { signedInUser } = useSignedInUser();
  const [universityProfileFetchStatus, setUniversityProfileFetchStatus] =
    useState<UniversityProfileFetchStatus>("loading");
  const [universityDataPanelProps, setUniversityDataPanelProps] =
    useState<UniversityDataPanelProps>();
  const { appState, setAppState } = useApplicationState();

  if (universityId === undefined || !isInteger(universityId)) {
    return (
      <NotFoundPage
        icon={SearchOffIcon}
        title="University not found"
        subheader="Profile you are looking for was deleted or does not exist"
      />
    );
  }

  const getProfile = async () => {
    const result = await sendGetUniversityProfileRequest(universityId);
    if (result.isSuccess) {
      const data: GetUniversityProfileResponse = result.data;
      const universityDataPanelProps: UniversityDataPanelProps = {
        userId: signedInUser.id,
        university: {
          id: data.id,
          nativeName: data.nativeName,
          englishName: data.englishName,
          linkToWebsite: data.linkToWebsite,
          cityName: data.cityName,
          countryName: data.countryName,
        },
        isFollowed: data.isFollowed,
      };
      setUniversityDataPanelProps(universityDataPanelProps);
      setUniversityProfileFetchStatus("success");
    } else {
      if (result.error.status === "NOT_FOUND") {
        setUniversityProfileFetchStatus("universityNotFound");
      } else if (result.error.status === "INTERNAL_SERVER_ERROR") {
        setUniversityProfileFetchStatus("serverError");
      } else if (result.error.status === "SERVICE_UNAVAILABLE") {
        setUniversityProfileFetchStatus("connectionError");
      }
    }
  };

  useEffect(() => {
    if (universityProfileFetchStatus === "success") {
      if (appState !== "success") {
        setAppState("success");
      }
    } else if (universityProfileFetchStatus === "connectionError") {
      setAppState("connectionError");
    } else if (universityProfileFetchStatus === "serverError") {
      setAppState("serverError");
    }
  }, [universityProfileFetchStatus]);

  useEffect(() => {
    setAppState("loading");
    getProfile();
  }, []);

  if (universityProfileFetchStatus === "universityNotFound") {
    return (
      <NotFoundPage
        icon={SearchOffIcon}
        title="University not found"
        subheader="Profile you are looking for was deleted or does not exist"
      />
    );
  } else if (
    universityProfileFetchStatus === "success" &&
    universityDataPanelProps !== undefined
  ) {
    return (
      <Grid container minHeight="100vh" sx={{ backgroundColor: "#eeececff" }}>
        <Grid size={{ xs: 12, lg: 3 }}>
          {!isLgScreen && <Navbar />}
          <UniversityDataPanel {...universityDataPanelProps} />
          {!isLgScreen && <FeedPanel universityId={universityId} />}
        </Grid>
        <Grid size={{ xs: 0, lg: 9 }}>
          <Box sx={{ minHeight: "100%" }}>
            {isLgScreen && (
              <>
                <Navbar />
                <FeedPanel universityId={universityId} />
              </>
            )}
          </Box>
        </Grid>
      </Grid>
    );
  }
};

export default UniversityProfilePage;
