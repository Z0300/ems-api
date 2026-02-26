package com.api.ems.registrations;

import com.api.ems.common.SecurityRules;
import com.api.ems.entities.enums.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RegistrationSecurityRules implements SecurityRules {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry
                .requestMatchers(HttpMethod.POST, "/api/registrations").hasRole(Role.ATTENDEE.name())

                .requestMatchers(HttpMethod.GET, "/api/registrations/**").hasAnyRole(Role.ORGANIZER.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/api/registrations/**").hasAnyRole(Role.ORGANIZER.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/registrations/**").hasAnyRole(Role.ORGANIZER.name(), Role.ADMIN.name());

    }
}
