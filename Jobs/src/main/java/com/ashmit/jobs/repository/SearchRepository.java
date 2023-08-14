package com.ashmit.jobs.repository;

import com.ashmit.jobs.model.Post;

import java.util.List;

public interface SearchRepository {
    List<Post> findbyByText(String text);
}
