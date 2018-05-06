package com.example.GitHub_API.web;

import com.example.GitHub_API.domain.GithubData;
import com.example.GitHub_API.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RepositoryController {

    private RepositoryService repositoryService;

    @Autowired
    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/repositories/{user}/{repository-name}")
    public ResponseEntity<Object> getRepositoryByUserAndRepoName(
            @PathVariable("user") String user,
            @PathVariable("repository-name") String repositoryname) {
        GithubData response = repositoryService.getRepositoryByUserAndRepoName(user, repositoryname);
        if (response.getError() != null) {
            return new ResponseEntity<>(response.getError(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

