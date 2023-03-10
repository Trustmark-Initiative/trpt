package edu.gatech.gtri.trustmark.grails.trpt.init

import edu.gatech.gtri.trustmark.grails.OAuth2LoginCustomizer
import edu.gatech.gtri.trustmark.grails.oidc.service.WebClientConfigurer
import edu.gatech.gtri.trustmark.grails.trpt.domain.User
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role
import edu.gatech.gtri.trustmark.grails.trpt.service.user.UserService
import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.reactive.function.client.WebClient

import static org.gtri.fj.data.Option.somes

class Application extends GrailsAutoConfiguration implements org.springframework.context.EnvironmentAware {

    static void main(final String[] argumentArray) {
        GrailsApp.run(Application, argumentArray)
    }

    @Override
    void setEnvironment(final Environment environment) {
        final Properties properties = new Properties()
        final InputStream inputStream = getClass().classLoader.getResourceAsStream('trpt.properties')
        if (inputStream) {
            properties.load(inputStream)
        } else {
            throw new RuntimeException('The system could not find "trpt.properties".')
        }
        ((ConfigurableEnvironment) environment).getPropertySources().addFirst(new MapPropertySource('trpt.properties', (Map<String, Object>) (Map) properties));
    }

    @Bean
    UserService userService() {
        return new UserService()
    }

    @Bean
    SecurityFilterChain filterChain(
            final HttpSecurity httpSecurity)
            throws Exception {

        final OAuth2UserService oauth2UserService = new DefaultOAuth2UserService();

        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .regexMatchers("/assets/.*").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(new OAuth2LoginCustomizer({ defaultOAuth2User ->
                    User.withTransaction {
                        userService().upsertHelper(
                                defaultOAuth2User.getName(),
                                defaultOAuth2User.getAttributes().get("family_name"),
                                defaultOAuth2User.getAttributes().get("given_name"),
                                defaultOAuth2User.getAttributes().get("email"),
                                somes(org.gtri.fj.data.List.iterableList(defaultOAuth2User.getAuthorities()).map(GrantedAuthority::getAuthority).map(Role::fromValue)))
                    }
                }))

        httpSecurity.build()
    }

    @Bean
    WebClient webClient() {
        return WebClientConfigurer.webClient();
    }
}
