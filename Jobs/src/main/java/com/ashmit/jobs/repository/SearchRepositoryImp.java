package com.ashmit.jobs.repository;

import com.ashmit.jobs.model.Post;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;


import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.AggregateIterable;
import org.springframework.stereotype.Service;

@Service
public class SearchRepositoryImp implements SearchRepository{

    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter convertor;

    @Override
    public List<Post> findbyByText(String text) {
        final List<Post> posts = new ArrayList<>();

        MongoDatabase database = client.getDatabase("Jobs");
        MongoCollection<Document> collection = database.getCollection("JobPosts");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query", text)
                                        .append("path", Arrays.asList("techs", "desc", "profile")))),
                new Document("$sort",
                        new Document("exp", -1L)),
                new Document("$limit", 5L)));

        result.forEach(doc -> posts.add(convertor.read(Post.class, doc)));

        return posts;
    }
}
