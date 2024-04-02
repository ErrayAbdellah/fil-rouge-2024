package com.mobi.mobe.service.implementations;

import com.mobi.mobe.dto.CommentDTO;
import com.mobi.mobe.entities.Comment;
import com.mobi.mobe.repository.CommentRepository;
import com.mobi.mobe.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<CommentDTO> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Comment not found with id: " + commentId);
        }
        Comment existingComment = optionalComment.get();
        existingComment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(existingComment);
        return modelMapper.map(updatedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.delete(optionalComment.get());
    }

}
