package com.example.Github_API.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class OwnerData {

    private String login;
    private Boolean site_admin;
}
