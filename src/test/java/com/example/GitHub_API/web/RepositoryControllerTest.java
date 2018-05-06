package com.example.GitHub_API.web;

import com.example.GitHub_API.GitHubApiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {GitHubApiApplication.class}
)
@AutoConfigureMockMvc
public class RepositoryControllerTest {
    private static final String URL = "/getRepository/{owner}/{repo}";
    private static final String USERNAME = "soldierPL";
    private static final String ERROR_USERNAME = "soldierEN";
    private static final String REPOSITORY = "restService";
    private static final String COMMITS = "/commits";
    private static final String NOT_FOUND = "404 Not Found";
    @Autowired
    private MockMvc mockMvc;

    public RepositoryControllerTest() {
    }

    @Test
    public void shouldReturnValidResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL, new Object[]{USERNAME, REPOSITORY}))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner.login", new Object[0])
                        .value(USERNAME));
    }

    @Test
    public void shouldReturn404Error() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL,
                new Object[]{ERROR_USERNAME, REPOSITORY}))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$", new Object[0])
                        .value(NOT_FOUND));
    }

    @Test
    public void shouldReturnListOfCommits() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + COMMITS,
                new Object[]{USERNAME, REPOSITORY}))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].commit.author.name",
                        new Object[0]).value(USERNAME));
    }

    @Test
    public void shouldReturn403ErrorWhenAskingForCommits() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL,
                new Object[]{ERROR_USERNAME, REPOSITORY}))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$", new Object[0])
                        .value("404 Not Found"));
    }
}
