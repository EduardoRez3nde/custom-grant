package com.customgrant.custom_grant.configuration;

import com.customgrant.custom_grant.configuration.kafka.event.LoginAccessEvent;
import com.customgrant.custom_grant.dtos.AccessLoginDTO;
import com.customgrant.custom_grant.dtos.RoleDTO;
import com.customgrant.custom_grant.entities.Role;
import com.customgrant.custom_grant.entities.User;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.*;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class TokenConfiguration {

    @Value("${spring.security.resource-server.jwt.issuer-uri}")
    private String issuerUri;

    private final ApplicationEventPublisher eventPublisher;

    public TokenConfiguration(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .refreshTokenTimeToLive(Duration.ofDays(40))
                .reuseRefreshTokens(true)
                .idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
                .build();
    }

    @Bean
    public OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator() {

        System.out.println("✅ ✅ ✅ ✅ token Generator✅ ✅ ✅ ✅ ✅ ✅ ");

        final NimbusJwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource());
        final JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        jwtGenerator.setJwtCustomizer(jwtCustomizer());
        final OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        final OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {

        System.out.println("### Token customizer foi invocado");

        return context -> {

            System.out.println("✅ ✅ ✅ ✅ Token customizer foi invocado✅ ✅ ✅ ✅ ✅ ✅ ");
            System.out.println("### Tipo de token: " + context.getTokenType().getValue());

            if (context.getTokenType().getValue().equals("access_token")) {

                final Authentication user = context.getPrincipal();
                final User principal = (User) user.getPrincipal();

                final List<String> authorities = user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                                .toList();

                context.getClaims()
                        .issuer(issuerUri)
                        .claim("username", user.getName())
                        .claim("authorities", authorities)
                        .claim("scope", context.getAuthorizedScopes())
                        .expiresAt(Instant.now().plus(Duration.ofHours(1)));
                System.out.println("Emitindo token via authorization_code, enviando mensagem para Kafka");

                AccessLoginDTO loginInfo = AccessLoginDTO.from(principal.getId().toString(), user.getName(), authorities.getFirst());
                System.out.println("🔥 Publicando evento LoginAccessEvent: " + loginInfo);
                eventPublisher.publishEvent(new LoginAccessEvent(this, loginInfo));
            }
        };
    }
    @Bean
    public JwtEncoder jwtEncoder(final JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        final RSAKey rsaKey = generateRsa();
        final JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public static RSAKey generateRsa() {

        final KeyPair keyPair = generateRsaKeyPair();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString()).build();
    }

    @Bean
    public static KeyPair generateRsaKeyPair() {
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
