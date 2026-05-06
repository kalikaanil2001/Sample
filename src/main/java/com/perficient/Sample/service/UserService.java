package com.perficient.Sample.service;

import com.perficient.Sample.dto.UserDto;
import com.perficient.Sample.exception.UserNotFoundException;
import com.perficient.Sample.model.User;
import com.perficient.Sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> createUser(UserDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .build();
        return userRepository.save(user);
    }

    public Mono<User> getUser(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Flux<User> searchUsers(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public Mono<User> updateUser(String id, UserDto dto) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .flatMap(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());
                    existing.setAge(dto.getAge());
                    return userRepository.save(existing);
                });
    }

    public Mono<Void> deleteUser(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .flatMap(userRepository::delete);
    }
}
