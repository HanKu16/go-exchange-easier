package com.go_exchange_easier.backend.core.domain.user.avatar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

public class AvatarValidator implements ConstraintValidator<ValidAvatar, MultipartFile> {

    @Override
    public void initialize(ValidAvatar constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".webp")) {
            context.buildConstraintViolationWithTemplate("File has to have .webp extension.")
                    .addConstraintViolation();
            return false;
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("image/webp")) {
            context.buildConstraintViolationWithTemplate(
                    "Invalid MIME (expected was image/webp).")
                    .addConstraintViolation();
            return false;
        }
        try (InputStream inputStream = file.getInputStream()) {
            byte[] header = new byte[12];
            if (inputStream.read(header) < 12) {
                context.buildConstraintViolationWithTemplate(
                        "File is broken or too short.")
                        .addConstraintViolation();
                return false;
            }
            boolean isRiff = header[0] == 'R' && header[1] == 'I' &&
                    header[2] == 'F' && header[3] == 'F';
            boolean isWebP = header[8] == 'W' && header[9] == 'E' &&
                    header[10] == 'B' && header[11] == 'P';
            if (!isRiff || !isWebP) {
                context.buildConstraintViolationWithTemplate(
                        "File content does not match extension .webp.")
                        .addConstraintViolation();
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}