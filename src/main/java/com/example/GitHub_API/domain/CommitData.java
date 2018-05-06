package com.example.GitHub_API.domain;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CommitData {

    private String url;
    private String commit;
}
