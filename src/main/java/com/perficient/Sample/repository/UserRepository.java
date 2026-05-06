package com.perficient.Sample.repository;

import com.perficient.Sample.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Flux<User> findByNameContainingIgnoreCase(String name);
}
