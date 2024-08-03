package com.my_video_site.rest_api_mongodb.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.my_video_site.rest_api_mongodb.Models.User;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
