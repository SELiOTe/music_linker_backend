package com.seliote.mlb.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seliote.mlb.common.domain.eunm.RoleNameEnum;
import com.seliote.mlb.common.domain.resp.Resp;
import com.seliote.mlb.common.util.NetworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.seliote.mlb.common.domain.resp.Resp.*;

/**
 * Spring Security 配置
 *
 * @author seliote
 * @version 2021-07-04
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;
    private final JwsFilter jwsFilter;

    @Autowired
    public AuthConfig(ObjectMapper objectMapper,
                      JwsFilter jwsFilter) {
        this.objectMapper = objectMapper;
        this.jwsFilter = jwsFilter;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final var permitAll = new String[]{"/auth/**", "/actuator/**"};
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(permitAll).permitAll()
                .and().authorizeRequests().antMatchers("/admin/**")
                .hasAnyAuthority(RoleNameEnum.admin.name())
                .and().authorizeRequests().antMatchers("/**")
                .hasAnyAuthority(RoleNameEnum.user.name())
                .and().authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint((req, resp, e) -> {
                    log.warn("Unauthorized for {} from {}",
                            NetworkUtils.getRequestUri().orElse("null"),
                            NetworkUtils.getRequestIp().orElse("null"));
                    resp.getWriter().write(
                            objectMapper.writeValueAsString(Resp.resp(UNAUTHORIZED_CODE, UNAUTHORIZED_MSG)));
                })
                .accessDeniedHandler((req, resp, e) -> {
                    log.warn("Forbidden for {} from {}",
                            NetworkUtils.getRequestUri().orElse("null"),
                            NetworkUtils.getRequestIp().orElse("null"));
                    resp.getWriter().write(
                            objectMapper.writeValueAsString(Resp.resp(FORBIDDEN_CODE, FORBIDDEN_MSG)));
                });
        http.addFilterBefore(jwsFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 注入 PasswordEncoder Bean
     *
     * @return BCryptPasswordEncoder 对象
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
