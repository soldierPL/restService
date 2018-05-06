package com.example.GitHub_API.service;

import com.example.GitHub_API.domain.CommitData;
import com.example.GitHub_API.domain.GithubData;
import com.example.GitHub_API.errors.ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RepositoryService {

    private final static String URL = "https://api.github.com/repos/{owner}/{repo}";
    private static RestTemplate restTemplate;

    @Autowired
    public RepositoryService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public GithubData getRepositoryByUserAndRepoName(String userName, String repoName) {
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

    public List<CommitData> getCommitsByUserAndRepoName(String username, String repositoryName) {
        try {
            CommitData[] response = restTemplate.getForObject(URL + "/commits",
                    CommitData[].class, username, repositoryName);
            List<CommitData> commitDataList = Arrays.asList(response);
            return commitDataList.size() > 3 ? commitDataList.subList(0,3)
                    : commitDataList;
        } catch (HttpClientErrorException ex) {
            throw new ErrorException(ex.getMessage());
        }
    }
}