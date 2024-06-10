package edu.gatech.gtri.trustmark.grails.oidc;

import edu.gatech.gtri.trustmark.grails.OidcLoginCustomizer;
import edu.gatech.gtri.trustmark.grails.oidc.service.WebClientConfigurer;
import edu.gatech.gtri.trustmark.grails.trpt.service.ApplicationProperties;

import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role;
import edu.gatech.gtri.trustmark.grails.trpt.service.user.UserService;
import grails.core.GrailsApplication;
import grails.core.support.GrailsApplicationAware;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

import java.net.URI;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;

import static org.gtri.fj.data.Option.somes;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig implements GrailsApplicationAware {

    GrailsApplication grailsApplication;
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.scope}")
    private String scope;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("keycloak")
                .clientId(clientId)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(redirectUri)
                .scope(scope)
                .authorizationUri(issuerUri + "/protocol/openid-connect/auth")
                .tokenUri(issuerUri + "/protocol/openid-connect/token")
                .userInfoUri(issuerUri + "/protocol/openid-connect/userinfo")
                .userNameAttributeName("preferred_username")
                .jwkSetUri(issuerUri + "/protocol/openid-connect/certs")
                .clientName("Keycloak")
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }


    @Bean
    UserService userService() {
        return new UserService();
    }

    @Bean
    SecurityFilterChain filterChain(
            final HttpSecurity httpSecurity)
            throws Exception {

        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .regexMatchers("/assets/.*").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(new OidcLoginCustomizer(defaultOAuth2User -> {
                        userService().upsertHelper(
                                defaultOAuth2User.getName(),
                                (String)defaultOAuth2User.getAttributes().get("family_name"),
                                (String)defaultOAuth2User.getAttributes().get("given_name"),
                                (String)defaultOAuth2User.getAttributes().get("email"),
                                somes(org.gtri.fj.data.List.iterableList(defaultOAuth2User.getAuthorities()).map(GrantedAuthority::getAuthority).map(Role::fromValue)));
                }))
                .logout()
                .logoutSuccessHandler(oidcLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");

        return httpSecurity.build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {

        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(
                        this.clientRegistrationRepository());

        String redirectUrl = (String) grailsApplication.getConfig().get(ApplicationProperties.propertyNameServerUrl);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri(URI.create(redirectUrl));

        return oidcLogoutSuccessHandler;
    }

    @Bean
    WebClient webClient() {
        return WebClientConfigurer.webClient();
    }

    @Override
    public void setGrailsApplication(GrailsApplication grailsApplication) {
        this.grailsApplication = grailsApplication;
    }
}
