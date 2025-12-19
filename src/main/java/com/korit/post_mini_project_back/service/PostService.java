package com.korit.post_mini_project_back.service;

import com.korit.post_mini_project_back.dto.request.CreatePostReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final FileService fileService;

    public void createPost(CreatePostReqDto dto) {
        fileService.upload("post", dto.getFiles());
    }

}
