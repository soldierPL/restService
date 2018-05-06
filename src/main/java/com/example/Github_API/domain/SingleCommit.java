package com.example.Github_API.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SingleCommit {

    private AuthorData author;
    private AuthorData commiter;
    private String message;
}
