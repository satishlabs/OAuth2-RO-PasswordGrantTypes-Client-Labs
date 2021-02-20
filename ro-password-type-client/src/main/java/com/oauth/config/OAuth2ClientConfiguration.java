package com.oauth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfiguration {
	
	@Autowired
	private OAuth2ClientContext auth2ClientContext;
	
	@Bean
	public OAuth2ProtectedResourceDetails resourceDetails() {
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		
		resourceDetails.setClientId("oauth2server");
		resourceDetails.setTokenName("oauth_token");
		resourceDetails.setClientId("satishapp");
		resourceDetails.setClientSecret("satish123");
		resourceDetails.setGrantType("password");
		
		resourceDetails.setUsername("satish");
		resourceDetails.setPassword("satish");
		
		resourceDetails.setAccessTokenUri("http://localhost:12345/oauth/token");
		resourceDetails.setScope(Arrays.asList("read_profile"));
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);

		return resourceDetails;
	}
	
	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate() {
		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails(), auth2ClientContext);
		
		AccessTokenProviderChain providerChain = new AccessTokenProviderChain(Arrays.asList(new ResourceOwnerPasswordAccessTokenProvider()));
		
		template.setAccessTokenProvider(providerChain);
		
		return template;
	}
}
