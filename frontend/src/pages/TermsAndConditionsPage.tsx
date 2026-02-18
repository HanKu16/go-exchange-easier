import {
  Grid,
  Box,
  Container,
  Typography,
  Paper,
  Stack,
  GlobalStyles,
  Avatar,
  Divider,
} from "@mui/material";
import goExchangeEasierCaptionImage from "../assets/caption.png";
import earthImage from "../assets/earth.png";
import registrationPageTextImage from "../assets/text.png";
import React from "react";
import GavelIcon from "@mui/icons-material/Gavel";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import VerifiedUserIcon from "@mui/icons-material/VerifiedUser";
import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";
import SecurityIcon from "@mui/icons-material/Security";

type SectionProps = {
  icon: React.ReactNode;
  title: string;
  children: React.ReactNode;
};

const Section: React.FC<SectionProps> = ({ icon, title, children }) => (
  <Box sx={{ mb: 5 }}>
    <Stack direction="row" spacing={2} sx={{ mb: 1.5 }}>
      <Avatar
        sx={{
          bgcolor: "rgba(25, 118, 210, 0.08)",
          color: "primary.main",
          width: 42,
          height: 42,
        }}
      >
        {icon}
      </Avatar>
      <Typography
        variant="h6"
        sx={{
          fontWeight: 700,
          alignSelf: "center",
          color: "#1e293b",
          fontSize: "1.1rem",
        }}
      >
        {title}
      </Typography>
    </Stack>
    <Box sx={{ pl: { xs: 0, sm: 7.2 } }}>
      <Typography
        variant="body1"
        sx={{
          color: "#64748b",
          lineHeight: 1.75,
          fontSize: "0.975rem",
        }}
      >
        {children}
      </Typography>
    </Box>
  </Box>
);

const TermsAndConditions: React.FC = () => {
  const lastUpdated: string = "February 4, 2026";
  const appName: string = "Go Exchange Easier";

  return (
    <Box
      sx={{ bgcolor: "#f8fafc", py: 10, minHeight: "100vh", borderRadius: 10 }}
    >
      <Container maxWidth="sm">
        <Box sx={{ mb: 6, textAlign: "center" }}>
          <Typography
            variant="h3"
            sx={{
              fontWeight: 900,
              color: "#0f172a",
              letterSpacing: "-0.02em",
            }}
          >
            Terms of Service
          </Typography>
          <Typography variant="body2" sx={{ color: "#94a3b8", mt: 2 }}>
            Version 1.0 • Last Modified: {lastUpdated}
          </Typography>
        </Box>

        <Paper elevation={0}>
          <Section
            icon={<GavelIcon fontSize="small" />}
            title="1. The Agreement"
          >
            By accessing <strong>{appName}</strong>, you enter into a legally
            binding agreement. If you do not agree to these terms, please
            discontinue use of our services immediately.
          </Section>

          <Section
            icon={<AccountCircleIcon fontSize="small" />}
            title="2. Your Identity"
          >
            You are responsible for all activity on your account. You must be at
            least 13 years old. Providing false information or impersonating
            others is strictly prohibited.
          </Section>

          <Section
            icon={<SecurityIcon fontSize="small" />}
            title="3. Content Ownership"
          >
            You keep the rights to what you post. However, by sharing content,
            you grant us a worldwide license to host and display it so your
            followers can see your updates.
          </Section>

          <Section
            icon={<VerifiedUserIcon fontSize="small" />}
            title="4. Prohibited Behavior"
          >
            We maintain a zero-tolerance policy for harassment, hate speech, and
            illegal activities. Play fair, be kind, and keep the community
            positive.
          </Section>
          <Divider sx={{ my: 4, opacity: 0.6 }} />
          <Box
            sx={{
              display: "flex",
              gap: 2,
              p: 3,
              bgcolor: "#fffbeb",
              borderRadius: "16px",
              border: "1px solid #fef3c7",
            }}
          >
            <ErrorOutlineIcon sx={{ color: "#d97706" }} />
            <Box>
              <Typography
                variant="subtitle2"
                sx={{ color: "#92400e", fontWeight: 700 }}
              >
                A Note on Updates
              </Typography>
              <Typography
                variant="body2"
                sx={{ color: "#b45309", mt: 0.5, lineHeight: 1.6 }}
              >
                We may modify these terms as our platform evolves. We will
                notify you of any major changes associated with your account.
              </Typography>
            </Box>
          </Box>
        </Paper>
      </Container>
    </Box>
  );
};
const TermsAndConditionsPage = () => {
  return (
    <>
      <GlobalStyles
        styles={{ body: { backgroundColor: "#0f1c2e", overflowX: "hidden" } }}
      />
      <Grid
        container
        component="main"
        sx={{ minHeight: "100vh", backgroundColor: "#182c44" }}
      >
        <Grid
          size={{ lg: 6 }}
          sx={{
            display: { xs: "none", lg: "flex" },
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            p: 4,
            height: "100vh",
            position: "sticky",
            top: 0,
          }}
        >
          <Stack spacing={4} alignItems="center" sx={{ maxWidth: "80%" }}>
            <img
              src={goExchangeEasierCaptionImage}
              alt="Go Exchange Easier"
              style={{
                maxWidth: "100%",
                maxHeight: "15vh",
                objectFit: "contain",
              }}
            />
            <img
              src={earthImage}
              alt="Earth"
              style={{
                maxWidth: "100%",
                maxHeight: "40vh",
                objectFit: "contain",
                animation: "float 6s ease-in-out infinite",
              }}
            />
            <img
              src={registrationPageTextImage}
              alt="Find people..."
              style={{
                maxWidth: "100%",
                maxHeight: "15vh",
                objectFit: "contain",
              }}
            />
          </Stack>
        </Grid>
        <Grid
          size={{ xs: 12, lg: 5 }}
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            py: 2,
            minHeight: { lg: "100vh" },
          }}
        >
          <TermsAndConditions />
        </Grid>
      </Grid>
    </>
  );
};

export default TermsAndConditionsPage;
