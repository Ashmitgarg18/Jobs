package com.ashmit.jobs.controller;

import com.ashmit.jobs.repository.PostRepository;
import com.ashmit.jobs.model.Post;
import com.ashmit.jobs.repository.SearchRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    @Autowired
    PostRepository repo;

    @Autowired
    SearchRepositoryImp srepo;

    @GetMapping("/posts")
    public List<Post> getAllPosts(){
        return repo.findAll();
    }

    @GetMapping("/posts/{text}")
    public List<Post> search(@PathVariable String text){
//        repo.findbyByText()
        return srepo.findbyByText(text);
    }

    @PostMapping("/post")
    public Post addPost(@RequestBody Post post){
        return repo.save(post);
    }

    @PutMapping("/posts/{_id}")
    public Post updatePost(@RequestBody Post newPost, @PathVariable String _id){
        Optional<Post> o = repo.findById(_id)
                .map(post -> {
                    post.setProfile(newPost.getProfile());
                    return repo.save(post);
                });
        return o.get();

//        Optional<Post> optionalPost = repo.findById(_id);
//        Post post = optionalPost.get();
//        post.setProfile(newPost.getProfile());
//        return repo.save(post);

//                .orElseGet(() ->{
//                    newPost.setId(_id);
//                    return repo.save(newPost);
//                });

    }

    @DeleteMapping("/posts/{_id}")
    void deleteEmployee(@PathVariable String _id) {
        repo.deleteById(_id);
    }

}
