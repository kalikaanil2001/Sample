package com.perficient.Sample.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Email must be valid")
    private String email;

    @Min(value = 1, message = "Age must be at least 1")
    private int age;
}

