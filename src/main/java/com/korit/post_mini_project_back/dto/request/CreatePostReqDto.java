package com.korit.post_mini_project_back.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreatePostReqDto {

    private String visibility;
    private String content;
    private List<MultipartFile> files;



}
