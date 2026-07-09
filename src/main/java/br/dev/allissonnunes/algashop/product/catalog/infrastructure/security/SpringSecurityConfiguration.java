package br.dev.allissonnunes.algashop.product.catalog.infrastructure.security;

import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.boot.health.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.security.autoconfigure.actuate.web.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
class SpringSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(healthMatcher(), additionalHealthPathsMatcher()).permitAll()
//                        .requestMatchers("/api/v1/products/{productId}/restock", "/api/v1/products/{productId}/withdraw").hasAuthority("SCOPE_products:stock:write")
//                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").hasAuthority("SCOPE_products:read")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAuthority("SCOPE_products:write")
//                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").hasAuthority("SCOPE_categories:read")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasAuthority("SCOPE_categories:write")
                        .anyRequest().authenticated())
                .build();
    }

    private RequestMatcher healthMatcher() {
        return EndpointRequest.to(HealthEndpoint.class);
    }

    private RequestMatcher additionalHealthPathsMatcher() {
        return EndpointRequest.toAdditionalPaths(WebServerNamespace.SERVER, HealthEndpoint.class);
    }

}
