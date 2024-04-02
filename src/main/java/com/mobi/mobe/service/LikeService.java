package com.mobi.mobe.service;

import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    void likePost(long postId, long userId);
//    void unlikePost(long postId, long userId);
}
