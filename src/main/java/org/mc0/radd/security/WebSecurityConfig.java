package org.mc0.radd.security;

import org.mc0.radd.RaddTokenProviderService;
import org.mc0.radd.model.RaddUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private RaddTokenProviderService raddTokenProviderService;

    @Autowired
    private ClientRegistrationConfig clientRegistrationConfig;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authorizeRequests) ->
            authorizeRequests
                .requestMatchers("/oauth2/authorization/**").permitAll()
                .requestMatchers("/login/oauth2/code/**").permitAll()
                .anyRequest().authenticated()
        )
        .oauth2Login(oauth2 -> oauth2
            .successHandler((request, response, authentication) -> {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                RaddUser user = new RaddUser();
                user.setUsername(oauth2User.getAttribute("name"));
                user.setEmail(oauth2User.getAttribute("email"));
                user.setId(oauth2User.getAttribute("id"));

                String token = raddTokenProviderService.generateToken(user);

                // Clear security context
                SecurityContextHolder.clearContext();
                // Clear session
                request.getSession().invalidate();

                response.setContentType("application/json");
                response.getWriter().write("{\"token\":\"" + token + "\"}");
            }));
    return http.build();
}
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                this.githubClientRegistration(),
                this.googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(clientRegistrationConfig.getGoogleClientId())
                .clientSecret(clientRegistrationConfig.getGoogleClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(clientRegistrationConfig.getGoogleRedirectUri())
                .scope("openid", "profile", "email", "address", "phone")
                .authorizationUri(clientRegistrationConfig.getGoogleAuthUri())
                .tokenUri(clientRegistrationConfig.getGoogleTokenUri())
                .userInfoUri(clientRegistrationConfig.getGoogleUserInfoUri())
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri(clientRegistrationConfig.getGoogleJwkSetUri())
                .clientName("Google")
                .build();
    }

private ClientRegistration githubClientRegistration() {
    return ClientRegistration.withRegistrationId("github")
            .clientId(clientRegistrationConfig.getGithubClientId())
            .clientSecret(clientRegistrationConfig.getGithubClientSecret())
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri(clientRegistrationConfig.getGithubRedirectUri())
            .scope("read:user", "user:email")
            .authorizationUri(clientRegistrationConfig.getGithubAuthUri())
            .tokenUri(clientRegistrationConfig.getGithubTokenUri())
            .userInfoUri(clientRegistrationConfig.getGithubUserInfoUri())
            .userNameAttributeName("id")
            .clientName("GitHub")
            .build();
}


}
