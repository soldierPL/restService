package com.example.Github_API.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class GithubData {

    private OwnerData owner;
    private String full_name;
    private String description;
    private String url;
    private String commits_url;
    private Integer watchers_count;
    @JsonIgnore
    private String error;
}
