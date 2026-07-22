package com.f1hub.f1communityhub.mapper;

import com.f1hub.f1communityhub.dto.CreatePostRequest;
import com.f1hub.f1communityhub.dto.PostResponse;
import com.f1hub.f1communityhub.entity.Post;
import com.f1hub.f1communityhub.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity(CreatePostRequest request, User user) {
        Post post = new Post();
        post.setUser(user);
        post.setRaceRound(request.getRaceRound());
        post.setContent(request.getContent());
        return post;
    }

    public PostResponse toResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getUser().getUsername(),
                post.getRaceRound(),
                post.getContent(),
                post.getCreatedAt()
        );
    }
}