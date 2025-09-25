package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.dto.universityMajor.GetUniversityMajorResponse;
import com.go_exchange_easier.backend.service.UniversityMajorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/universityMajors")
@RequiredArgsConstructor
@Tag(name = "University Major", description =
        "Operations related to university majors.")
public class UniversityMajorsController {

    private final UniversityMajorService universityMajorService;

    @GetMapping
    public ResponseEntity<List<GetUniversityMajorResponse>> getAll() {
        List<GetUniversityMajorResponse> response = universityMajorService.getAll();
        return ResponseEntity.ok(response);
    }

}
