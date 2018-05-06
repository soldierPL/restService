package com.example.Github_API.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class AuthorData {

    private String name;
    private String email;
    private LocalDateTime date;
}
