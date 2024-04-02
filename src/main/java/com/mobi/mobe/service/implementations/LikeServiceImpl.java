package com.mobi.mobe.service.implementations;

import com.mobi.mobe.entities.Like;
import com.mobi.mobe.entities.Post;
import com.mobi.mobe.entities.User;
import com.mobi.mobe.repository.LikeRepository;
import com.mobi.mobe.repository.PostRepository;
import com.mobi.mobe.repository.UserRepository;
import com.mobi.mobe.service.LikeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void likePost(long postId, long userId) {

        Like like = new Like();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Integer count = likeRepository.countUserLikes(userId, postId);
        boolean alreadyLiked = count != null && count > 0;

        if (!alreadyLiked) {
            // Logic to add a new like
           like.setPost(post);
           like.setUser(user);
           likeRepository.save(like);
        } else {
            // Logic to remove existing like
            likeRepository.deleteByPostAndUser(post, user);
//            System.out.println("Remove like");
        }
    }

//    @Override
//    @Transactional
//    public void unlikePost(long postId, long userId) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
//        likeRepository.deleteByPostAndUser(post, user);
//    }
//    @Transactional
    public void unlikePost(Post post, User user) {
        likeRepository.deleteByPostAndUser(post, user);
}
}