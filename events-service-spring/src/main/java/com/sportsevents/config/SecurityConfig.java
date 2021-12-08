package com.sportsevents.config;


import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

@KeycloakConfiguration
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        // return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/actuator/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/events").authenticated()
                .antMatchers(HttpMethod.GET, "/events/*").authenticated()
                .antMatchers(HttpMethod.GET, "/leaderboard").authenticated()
                .antMatchers(HttpMethod.POST, "/events/").hasAuthority("admin")
                .antMatchers(HttpMethod.POST, "/events/closeEvent").hasAnyAuthority("admin","facilitator")
                .antMatchers(HttpMethod.PATCH, "/events/addPlayer").hasAuthority("player")
                .antMatchers(HttpMethod.PATCH, "/events/removePlayer").hasAuthority("player")
                .antMatchers(HttpMethod.DELETE, "/events/*").hasAuthority("admin")
                .anyRequest().permitAll()
                .and().csrf().disable();
    }
}