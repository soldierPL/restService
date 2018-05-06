package com.example.GitHub_API.service;

import com.example.GitHub_API.domain.GithubData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RepositoryService {

    private final static String URL = "https://api.github.com/repos/{owner}/{repo}";
    private static RestTemplate restTemplate;

    @Autowired
    public RepositoryService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public static GithubData getRepositoryByUserAndRepoName(String userName, String repoName) {
        try {
            GithubData response = restTemplate
                    .getForObject(URL, GithubData.class, userName, repoName);

            return response;

        }catch (HttpClientErrorException ex) {
            GithubData errorResponse = new GithubData();
            errorResponse.setError(ex.getMessage());

            return errorResponse;
        }
    }
}