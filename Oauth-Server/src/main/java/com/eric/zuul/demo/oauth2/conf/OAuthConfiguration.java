package com.eric.zuul.demo.oauth2.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.StringUtils;

import java.util.*;


@Configuration
@EnableAuthorizationServer
@ConfigurationProperties(prefix = "security.clients")
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Getter @Setter
	private Map<String, SecuritySettings> settings = new HashMap<>();
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	    if(!settings.isEmpty()){
            InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
	        for(Map.Entry<String,SecuritySettings> entry:settings.entrySet()){
	            SecuritySettings settings = entry.getValue();
	            builder.withClient(Optional.of(settings.getClientId()).orElse(entry.getKey()))
                        .secret(settings.getSecret())
                        .scopes(StringUtils.toStringArray(settings.getScopes())).autoApprove(true)
                        .authorities(StringUtils.toStringArray(settings.getAuthorities()))
                        .authorizedGrantTypes(StringUtils.toStringArray(settings.getAuthorizedGrantTypes()))
                        .redirectUris(StringUtils.toStringArray(settings.getRedirectUris()));
            }
        }
    }

    @Getter
    @Setter
    public static class SecuritySettings {
	    private String clientId;
        private String secret;
        private List<String> scopes = new ArrayList<>();
        private List<String> authorities = new ArrayList<>();
        private List<String> authorizedGrantTypes = new ArrayList<>();
        private List<String> redirectUris = new ArrayList<>();
    }

	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        .tokenStore(jwtTokenStore())
        .tokenEnhancer(jwtTokenConverter())
        .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtTokenConverter());
    }
    
    @Bean
    protected JwtAccessTokenConverter jwtTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("springcloud123");
        return converter;
    }
}
