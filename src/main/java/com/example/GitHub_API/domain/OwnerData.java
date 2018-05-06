package com.example.GitHub_API.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data

public class OwnerData {

    private String login;
    private boolean site_admin;
}
