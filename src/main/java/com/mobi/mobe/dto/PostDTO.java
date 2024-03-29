package com.mobi.mobe.dto;

import com.mobi.mobe.enums.TypePost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private long id;
    private TypePost typePost;
    private String urlContent;
    private String content;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;
    private UserDTO user ;
}