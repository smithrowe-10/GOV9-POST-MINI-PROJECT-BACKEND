package com.korit.post_mini_project_back.controller;

import com.korit.post_mini_project_back.dto.request.CreatePostReqDto;
import com.korit.post_mini_project_back.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
                // URL에 자원의 이름을 쓰기 REST API 규칙
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // FORM_DATA는 이거씀 JSON은 RequestBody 쓰듯이
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(@ModelAttribute CreatePostReqDto dto) {
        System.out.println(dto);
        postService.createPost(dto);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/feeds")
    public ResponseEntity<?> getFeedList() {
        return ResponseEntity.ok(null);
    }

}
