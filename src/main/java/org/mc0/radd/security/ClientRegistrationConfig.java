package org.mc0.radd.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ClientRegistrationConfig {
    @Value("${github.client-id}")
    private String githubClientId;
    @Value("${github.client-secret}")
    private String githubClientSecret;
    @Value("${github.auth-uri}")
    private String githubAuthUri;
    @Value("${github.user-info-uri}")
    private String githubUserInfoUri;
    @Value("${github.token-uri}")
    private String githubTokenUri;
    @Value("${github.redirect-uri}")
    private String githubRedirectUri;

    @Value("${google.client-id}")
    private String googleClientId;
    @Value("${google.client-secret}")
    private String googleClientSecret;
    @Value("${google.auth-uri}")
    private String googleAuthUri;
    @Value("${google.user-info-uri}")
    private String googleUserInfoUri;
    @Value("${google.token-uri}")
    private String googleTokenUri;
    @Value("${google.redirect-uri}")
    private String googleRedirectUri;
    @Value("${google.jwkset-uri}")
    private String googleJwkSetUri;

}
