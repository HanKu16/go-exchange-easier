package com.go_exchange_easier.backend.core.domain.user.avatar;

import com.go_exchange_easier.backend.core.infrastracture.storage.BucketProperties;
import com.go_exchange_easier.backend.common.exception.FileUploadException;
import com.go_exchange_easier.backend.common.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;
import org.springframework.util.StringUtils;
import net.coobird.thumbnailator.Thumbnails;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final FileStorageService fileStorageService;
    private final BucketProperties bucketProperties;

    @Override
    public AvatarKeys add(int userId, MultipartFile file)  {
        int thumbSize = 64;
        String fileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(fileName);
        AvatarKeys keys = generateKeys(userId, extension);
        try {
            byte[] originalBytes = file.getBytes();
            fileStorageService.upload(bucketProperties.getUser(),
                    keys.original(), new ByteArrayInputStream(originalBytes),
                    originalBytes.length, file.getContentType());
            InputStream thumbResultStream = generateThumbnail(
                    new ByteArrayInputStream(originalBytes), thumbSize, thumbSize);
            byte[] thumbBytes = thumbResultStream.readAllBytes();
            fileStorageService.upload(bucketProperties.getUser(),
                    keys.thumbnail(), new ByteArrayInputStream(thumbBytes),
                    thumbBytes.length, "image/png");
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload avatar: " +
                    e.getMessage());
        }
        return keys;
    }

    @Override
    public boolean delete(String originalKey) {
        return fileStorageService.delete(bucketProperties.getUser(), originalKey) &&
                fileStorageService.delete(bucketProperties.getUser(),
                        getThumbnailKey(originalKey));
    }

    @Override
    public AvatarUrlSummary getUrl(String originalKey) {
        String originalAvatarUrl = fileStorageService.getPublicUrl(
                bucketProperties.getUser(), originalKey);
        String thumbnailAvatarUrl = fileStorageService.getPublicUrl(
                bucketProperties.getUser(), getThumbnailKey(originalKey));
        return new AvatarUrlSummary(originalAvatarUrl, thumbnailAvatarUrl);
    }

    private AvatarKeys generateKeys(int userId, String extension) {
        UUID uuid = UUID.randomUUID();
        String key = userId + "/" + uuid + "." + extension;
        String thumbKey = getThumbnailKey(key);
        return new AvatarKeys(key, thumbKey);
    }

    private String getThumbnailKey(String originalKey) {
        int dotIndex = originalKey.lastIndexOf('.');
        if (dotIndex == -1) {
            return originalKey + "_small";
        }
        return originalKey.substring(0, dotIndex)
                + "_small"
                + ".png";
    }

    private InputStream generateThumbnail(InputStream originalImage,
            int width, int height) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(width, height)
                .outputFormat("png")
                .outputQuality(0.8)
                .toOutputStream(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}
