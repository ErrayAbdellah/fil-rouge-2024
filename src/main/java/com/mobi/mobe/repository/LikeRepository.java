package com.mobi.mobe.repository;

import com.mobi.mobe.entities.Like;
import com.mobi.mobe.entities.Post;
import com.mobi.mobe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    void deleteByPostAndUser(Post post, User user);
    @Query(value = "SELECT COUNT(*) FROM `like` WHERE user_id = ?1 AND post_id = ?2", nativeQuery = true)
    Integer countUserLikes(long userId, long postId);

}
