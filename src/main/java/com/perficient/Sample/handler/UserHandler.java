package com.perficient.Sample.handler;

import com.perficient.Sample.dto.UserDto;
import com.perficient.Sample.model.User;
import com.perficient.Sample.service.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    private final Validator validator;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserDto.class)
                .flatMap(dto -> validate(dto))
                .flatMap(userService::createUser)
                .flatMap(user -> ServerResponse.status(HttpStatus.CREATED).bodyValue(user));
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        String id = request.pathVariable("id");
        return userService.getUser(id)
                .flatMap(user -> ServerResponse.ok().bodyValue(user));
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().body(userService.getAllUsers(), User.class);
    }

    public Mono<ServerResponse> search(ServerRequest request) {
        String name = request.queryParam("name").orElse("");
        return ServerResponse.ok().body(userService.searchUsers(name), User.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(UserDto.class)
                .flatMap(dto -> validate(dto))
                .flatMap(dto -> userService.updateUser(id, dto))
                .flatMap(user -> ServerResponse.ok().bodyValue(user));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return userService.deleteUser(id)
                .then(ServerResponse.noContent().build());
    }

    private Mono<UserDto> validate(UserDto dto) {
        Errors errors = new BeanPropertyBindingResult(dto, UserDto.class.getName());
        validator.validate(dto, errors);
        if (errors.hasErrors()) {
            return Mono.error(new ConstraintViolationException(errors.toString(), Set.of()));
        }
        return Mono.just(dto);
    }
}
