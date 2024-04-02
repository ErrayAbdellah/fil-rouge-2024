package com.mobi.mobe.controller;

import com.mobi.mobe.dto.PostDTO;
import com.mobi.mobe.dto.UserDTO;
import com.mobi.mobe.service.implementations.LikeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeServiceImpl likeService;

    @PostMapping("/{postId}/{userId}")
    public void likePost(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.likePost(postId, userId);
    }

//    @DeleteMapping("/{postId}/{userId}")
//    public void unlikePost(@PathVariable Long postId, @PathVariable Long userId) {
//        likeService.unlikePost(postId, userId);
//    }
}
