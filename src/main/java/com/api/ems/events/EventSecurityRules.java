package com.api.ems.events;

import com.api.ems.common.SecurityRules;
import com.api.ems.entities.enums.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class EventSecurityRules implements SecurityRules {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry
                .requestMatchers(HttpMethod.POST, "/api/events/**").hasRole(Role.ORGANIZER.name())
                .requestMatchers(HttpMethod.PUT, "/api/events/**").hasRole(Role.ORGANIZER.name())
                .requestMatchers(HttpMethod.DELETE, "/api/events/**").hasRole(Role.ORGANIZER.name());
    }
}
