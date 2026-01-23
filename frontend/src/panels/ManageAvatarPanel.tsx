import { Box, Button, Tooltip } from "@mui/material";
import React, { useState, useCallback, useRef } from "react";
import Cropper, { type Area } from "react-easy-crop";
import AddToPhotosIcon from "@mui/icons-material/AddToPhotos";
import getCroppedImg from "../utils/canvas-utils";
import {
  sendDeleteAvatarRequest,
  sendUploadAvatarRequest,
} from "../utils/user";
import { Slider, Typography, Stack, IconButton, Divider } from "@mui/material";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import RestartAltIcon from "@mui/icons-material/RestartAlt";
import { useSnackbar } from "../context/SnackBarContext";
import { useSignedInUser } from "../context/SignedInUserContext";

const ChangeAvatarPanel = () => {
  const [crop, setCrop] = useState({ x: 0, y: 0 });
  const [zoom, setZoom] = useState(1);
  const [croppedAreaPixels, setCroppedAreaPixels] = useState<Area | null>(null);
  const [imageUrl, setImageUrl] = useState<string | null>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);
  const { showAlert } = useSnackbar();
  const { signedInUser, setSignedInUser } = useSignedInUser();

  const onCropComplete = useCallback((_: Area, pixels: Area) => {
    setCroppedAreaPixels(pixels);
  }, []);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      const url = URL.createObjectURL(file);
      setImageUrl(url);
    }
    event.target.value = "";
  };

  const resetSelection = () => {
    setImageUrl(null);
    setCroppedAreaPixels(null);
    setZoom(1);
  };

  const handleAvatarUpload = async () => {
    if (!imageUrl || !croppedAreaPixels) return;
    try {
      const croppedImageBlob = await getCroppedImg(imageUrl, croppedAreaPixels);
      if (croppedImageBlob) {
        const formData = new FormData();
        formData.append("file", croppedImageBlob, "avatar.webp");
        showAlert("Uploading photo.", "info");
        const result = await sendUploadAvatarRequest(formData);
        if (result.isSuccess) {
          resetSelection();
          showAlert("Photo was successfully uploaded.", "success");
          setSignedInUser({
            ...signedInUser,
            avatarUrl: result.data.original,
          });
        } else {
          showAlert("Failed to upload photo. Please try again later.", "error");
        }
      }
    } catch (e) {
      showAlert("Failed to upload photo. Please try again later.", "error");
    }
  };

  const handleDeleteAvatar = async () => {
    showAlert("Deleting photo.", "info");
    const result = await sendDeleteAvatarRequest();
    if (result.isSuccess) {
      showAlert("Photo was successfully deleted.", "success");
      setSignedInUser({
        ...signedInUser,
        avatarUrl: undefined,
      });
    } else {
      showAlert("Failed to delete photo. Please try again later.", "error");
    }
  };

  return (
    <Box
      sx={{
        width: "100%",
        display: "flex",
        flexDirection: "column",
        alignItems: "flex-start",
      }}
    >
      <Box sx={{ width: "100%", mb: 3 }}>
        <Typography
          variant="h5"
          sx={{ fontWeight: 600, color: "#1a1a1a", mb: 0.5 }}
        >
          Profile Picture
        </Typography>
      </Box>

      <Divider sx={{ width: "100%", mb: 4 }} />
      <Stack direction="row" spacing={2} sx={{ mb: 4 }}>
        <input
          type="file"
          accept="image/*"
          style={{ display: "none" }}
          ref={fileInputRef}
          onChange={handleFileChange}
        />

        <Button
          variant="contained"
          startIcon={<AddToPhotosIcon />}
          onClick={() => fileInputRef.current?.click()}
          sx={{
            bgcolor: "#f4f4f4",
            color: "#333",
            boxShadow: "none",
            "&:hover": { bgcolor: "#e8e8e8", boxShadow: "none" },
          }}
        >
          {imageUrl ? "Change photo" : "Select photo"}
        </Button>

        {imageUrl && (
          <Button
            variant="contained"
            startIcon={<CheckCircleIcon />}
            onClick={handleAvatarUpload}
            sx={{ bgcolor: "#241058", "&:hover": { bgcolor: "#1a0b40" } }}
          >
            Apply changes
          </Button>
        )}
      </Stack>

      <Box
        sx={{
          display: "flex",
          width: "100%",
          gap: 4,
          flexDirection: { xs: "column", md: "row" },
        }}
      >
        {imageUrl ? (
          <Box sx={{ flex: 2, minWidth: { md: 500 } }}>
            <Box
              sx={{
                position: "relative",
                width: "100%",
                height: 450,
                borderRadius: "12px",
                overflow: "hidden",
                border: "1px solid #e0e0e0",
              }}
            >
              <Cropper
                image={imageUrl}
                crop={crop}
                zoom={zoom}
                aspect={1}
                onCropChange={setCrop}
                onCropComplete={onCropComplete}
                onZoomChange={setZoom}
                cropShape="round"
                showGrid={false}
              />
            </Box>
            <Box sx={{ mt: 3, maxWidth: 400 }}>
              <Stack direction="row" spacing={2} alignItems="center">
                <Typography variant="caption" sx={{ fontWeight: 600 }}>
                  ZOOM
                </Typography>
                <Slider
                  value={zoom}
                  min={1}
                  max={3}
                  step={0.1}
                  onChange={(_, value) => setZoom(value as number)}
                  sx={{ color: "#241058" }}
                />
                <Tooltip title="Reset zoom">
                  <IconButton size="small" onClick={() => setZoom(1)}>
                    <RestartAltIcon />
                  </IconButton>
                </Tooltip>
              </Stack>
            </Box>
          </Box>
        ) : (
          <Box
            sx={{
              flex: 2,
              height: 450,
              bgcolor: "#fafafa",
              borderRadius: "12px",
              border: "2px dashed #e0e0e0",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              flexDirection: "column",
              color: "text.secondary",
            }}
          >
            <AddToPhotosIcon sx={{ fontSize: 48, mb: 2, opacity: 0.3 }} />
            <Typography>No photo selected</Typography>
          </Box>
        )}
        <Box
          sx={{
            flex: 1,
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-between",
            py: 1,
          }}
        >
          {signedInUser.avatarUrl && (
            <Box
              sx={{ mt: { xs: 4, md: 0 }, p: 2, borderTop: "1px solid #eee" }}
            >
              <Typography
                variant="body2"
                sx={{ mb: 2, color: "text.secondary" }}
              >
                Do you want to remove your current photo?
              </Typography>
              <Button
                variant="text"
                color="error"
                startIcon={<DeleteOutlineIcon />}
                sx={{
                  textTransform: "none",
                  fontWeight: 600,
                  "&:hover": { bgcolor: "#fff5f5" },
                }}
                onClick={handleDeleteAvatar}
              >
                Remove photo
              </Button>
            </Box>
          )}
        </Box>
      </Box>
    </Box>
  );
};

export default ChangeAvatarPanel;
