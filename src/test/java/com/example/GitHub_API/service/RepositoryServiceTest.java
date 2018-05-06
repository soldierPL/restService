package com.example.GitHub_API.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.GitHub_API.domain.CommitData;
import com.example.GitHub_API.domain.GithubData;
import com.example.GitHub_API.domain.OwnerData;
import com.example.GitHub_API.errors.ErrorException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryServiceTest {
    private static final String URL = "https://api.github.com/repos/{owner}/{repo}";
    private static final String USER = "test-user";
    private static final String REPOSITORY = "test-repository";
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private RepositoryService repositoryService;

    public RepositoryServiceTest() {
    }

    @Test
    public void shouldReturnValidResponseForQuery() {
        OwnerData ownerData = new OwnerData();
        ownerData.setLogin("test_login");
        ownerData.setSite_admin(false);
        GithubData githubData = new GithubData();
        githubData.setFull_name("test_name");
        githubData.setOwner(ownerData);
        githubData.setDescription("test_description");

        Mockito.when(this
                .restTemplate
                .getForObject((String)ArgumentMatchers
                        .any(String.class), (Class)ArgumentMatchers
                        .eq(GithubData.class), new Object[]{ArgumentMatchers
                        .any(String.class), ArgumentMatchers
                        .any(String.class)}))
                .thenReturn(githubData);

        GithubData underTest = this.repositoryService.getRepositoryByUserAndRepoName("username", "repoName");
        Assertions.assertThat(underTest.getFull_name()).isEqualTo(githubData.getFull_name());
    }

    @Test
    public void shouldGetErrorWhen4xxFromGithub() {
        String errorMessage = "test_error";
        Mockito.when(this.restTemplate.getForObject(URL, GithubData.class, new Object[]{USER, REPOSITORY}))
                .thenThrow(new Throwable[]{
                        new HttpClientErrorException(HttpStatus.FORBIDDEN, errorMessage)});

        GithubData underTest = this
                .repositoryService
                .getRepositoryByUserAndRepoName(USER, REPOSITORY);

        Assertions.assertThat(underTest.getError()).isEqualTo(HttpStatus.FORBIDDEN.value() + " " + errorMessage);
    }

    @Test
    public void shouldReturnListOfCommits() {
        String url1 = "url_1";
        String url2 = "url_2";
        CommitData[] data = new CommitData[]{new CommitData(), new CommitData(), null};
        data[1].setUrl(url1);
        data[2] = new CommitData();
        data[2].setUrl(url2);

        Mockito
                .when(this
                        .restTemplate
                        .getForObject(URL, CommitData[].class, new Object[]{USER, REPOSITORY}))
                .thenReturn(data);

        List<CommitData> underTest = this
                .repositoryService
                .getCommitsByUserAndRepoName(USER, REPOSITORY);

        Assertions
                .assertThat(underTest.size())
                .isEqualTo(data.length);

        Assertions
                .assertThat((List)underTest
                        .stream()
                        .map(CommitData::getUrl)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .containsExactlyInAnyOrder(new String[]{url1, url2});
    }

    @Test(
            expected = ErrorException.class
    )
    public void shouldThrowErrorException() {
        String errorMessage = "test_error";
        Mockito.when(this
                .restTemplate
                .getForObject(URL, CommitData[].class, new Object[]{USER, REPOSITORY}))
                .thenThrow(new Throwable[]{new HttpClientErrorException(HttpStatus.FORBIDDEN, errorMessage)});

        this
                .repositoryService
                .getCommitsByUserAndRepoName("user", "repo");
    }
}
