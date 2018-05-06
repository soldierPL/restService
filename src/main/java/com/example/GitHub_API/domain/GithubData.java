package com.example.GitHub_API.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class GithubData {

    private String full_name;
    private String description;

    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("clone_url")
    private String CloneUrl;
    @JsonProperty("stargazers_count")
    private Long stars;
    @JsonIgnore
    private String error;

}
