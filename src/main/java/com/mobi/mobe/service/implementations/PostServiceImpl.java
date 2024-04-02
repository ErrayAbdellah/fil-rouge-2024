package com.mobi.mobe.service.implementations;

import com.mobi.mobe.dto.PostDTO;
import com.mobi.mobe.entities.Post;
import com.mobi.mobe.entities.User;
import com.mobi.mobe.repository.PostRepository;
import com.mobi.mobe.repository.UserRepository;
import com.mobi.mobe.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDTO createPost(PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);

        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDTO.class);
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        return modelMapper.map(post, PostDTO.class);
    }

    public PostDTO updatePost(long postId, PostDTO updatedPostDTO) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        modelMapper.map(updatedPostDTO, existingPost);

        postRepository.save(existingPost);

        return null;
    }

    public void deletePost(long postId) {
        postRepository.deleteById(postId);
    }
}
