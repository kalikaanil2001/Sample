package com.perficient.Sample.config;

import com.perficient.Sample.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler handler) {
        return RouterFunctions.route()
                .GET("/users", handler::getAll)
                .GET("/users/{id}", handler::getUser)
                .GET("/users/search", handler::search)
                .POST("/users", handler::createUser)
                .PUT("/users/{id}", handler::update)
                .DELETE("/users/{id}", handler::delete)
                .build();
    }
}