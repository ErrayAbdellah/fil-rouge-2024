package com.mobi.mobe.service;

import com.mobi.mobe.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CommentService {
    List<CommentDTO> getAllCommentsByPostId(Long postId);

    CommentDTO createComment(CommentDTO commentDTO);

    CommentDTO updateComment(Long commentId, CommentDTO commentDTO);

    void deleteComment(Long commentId);

}
