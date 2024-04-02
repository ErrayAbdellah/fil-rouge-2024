package com.mobi.mobe.service;

import com.mobi.mobe.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPosts();
    PostDTO getPostById(long postId);
    PostDTO updatePost(long postId, PostDTO postDTO);
    void deletePost(long postId);

}
