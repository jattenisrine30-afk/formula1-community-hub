package com.f1hub.f1communityhub.service;

import com.f1hub.f1communityhub.dto.CreatePostRequest;
import com.f1hub.f1communityhub.dto.PostResponse;
import com.f1hub.f1communityhub.entity.Post;
import com.f1hub.f1communityhub.entity.User;
import com.f1hub.f1communityhub.mapper.PostMapper;
import com.f1hub.f1communityhub.repository.PostRepository;
import com.f1hub.f1communityhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public PostResponse createPost(CreatePostRequest request, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        Post post = postMapper.toEntity(request, user);
        postRepository.save(post);

        return postMapper.toResponse(post);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void deletePost(UUID postId, String username) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post introuvable"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Vous ne pouvez supprimer que vos propres posts");
        }

        postRepository.delete(post);
    }
}