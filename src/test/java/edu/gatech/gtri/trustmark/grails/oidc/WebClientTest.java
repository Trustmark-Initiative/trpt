package edu.gatech.gtri.trustmark.grails.oidc;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.oidc.OidcClientRegistrar;
import edu.gatech.gtri.trustmark.v1_0.oidc.OidcClientRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class WebClientTest {

    @Test
    public void test() {
        try {
            final OidcClientRegistrar oidcClientRegistrar = FactoryLoader.getInstance(OidcClientRegistrar.class);
            final OidcClientRegistration oidcClientRegistration = oidcClientRegistrar.register(
                    "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJmOTYwNjhjYy0xODM0LTQzM2EtOTc3Zi1hNmI3ODNkNDI4ZjcifQ.eyJleHAiOjE2NzcxNzk1ODYsImlhdCI6MTY3NzA5MzE4NiwianRpIjoiZmE0ZjY3ZDgtYTAzNS00Zjc2LWI0OWUtYTNkYjUxNzJlZTQxIiwiaXNzIjoiaHR0cHM6Ly9rZXkudHJ1c3RtYXJraW5pdGlhdGl2ZS5vcmcvYXV0aC9yZWFsbXMvdHJ1c3RtYXJrIiwiYXVkIjoiaHR0cHM6Ly9rZXkudHJ1c3RtYXJraW5pdGlhdGl2ZS5vcmcvYXV0aC9yZWFsbXMvdHJ1c3RtYXJrIiwidHlwIjoiSW5pdGlhbEFjY2Vzc1Rva2VuIn0.XZGjX7nLJwsOXGOq0xeUAPrV142dT8xdXttGf83I6TE",
                    "Client Name",
                    "http://localhost:8080/1");

            final Mono<ClientRegistration> clientRegistrationMono = Mono.just(ClientRegistration
                    .withRegistrationId("test")
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .clientId(oidcClientRegistration.getClientId())
                    .clientName(oidcClientRegistration.getClientName())
                    .clientSecret(oidcClientRegistration.getClientSecret())
                    .issuerUri(oidcClientRegistration.getIssuerUri())
                    .jwkSetUri(oidcClientRegistration.getJwkSetUri())
//                    .redirectUri(oidcClientRegistration.getRedirectUriList().index(0))
//                    .scope((String[]) oidcClientRegistration.getScopeList().toArrayObject())
                    .tokenUri(oidcClientRegistration.getTokenUri())
                    .userInfoAuthenticationMethod(AuthenticationMethod.HEADER)
                    .userInfoUri(oidcClientRegistration.getUserInfoUri())
                    .build());

            final WebClient webClient = WebClient
                    .builder()
                    .defaultRequest(requestHeadersSpec -> requestHeadersSpec.attributes(attributesConsumer -> {
                        ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("test").accept(attributesConsumer);
                    }))
                    .filter(new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                            new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                                    registrationId1 -> clientRegistrationMono,
                                    new InMemoryReactiveOAuth2AuthorizedClientService(registrationId -> clientRegistrationMono))))
                    .exchangeStrategies(ExchangeStrategies
                            .builder()
                            .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(1000000))
                            .build())
                    .build();

            final ResponseEntity<String> responseEntity = webClient.get()
                    .uri("https://key.trustmarkinitiative.org/tat/trust-interoperability-profiles/2/?format=xml")
                    .headers(headers -> headers.set(HttpHeaders.ACCEPT, "text/xml"))
                    .acceptCharset(StandardCharsets.UTF_8)
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            System.out.println(responseEntity.toString());
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
}
