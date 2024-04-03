package com.mobi.mobe.service.implementations;

import com.mobi.mobe.dto.PostDTO;
import com.mobi.mobe.entities.Post;
import com.mobi.mobe.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class CreatePostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    @Test
    public void testCreatePost() {

        PostDTO postDTO = new PostDTO();
        Post post = new Post();

        when(modelMapper.map(postDTO, Post.class)).thenReturn(post);

        when(postRepository.save(post)).thenReturn(post);

        PostDTO result = postServiceImpl.createPost(postDTO);

        verify(modelMapper, times(1)).map(postDTO, Post.class);
        verify(postRepository, times(1)).save(post);
    }
}
