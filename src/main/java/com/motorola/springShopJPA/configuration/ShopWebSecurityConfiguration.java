package com.motorola.springShopJPA.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ShopWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin", "/admin*", "/admin/*")
                .hasAuthority("ROLE_ADMIN")
                .antMatchers("/index")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/login","/home","/create_user").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login_process")
                .defaultSuccessUrl("/index")
                .and()
                .logout()
                .logoutSuccessUrl("/home");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .roles("USER")
                .password(bCryptPasswordEncoder.encode("haslousera"))
                .and()
                .withUser("admin")
                .roles("ADMIN")
                .password(bCryptPasswordEncoder.encode("hasloadmina"));
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select u.email, u.password, enabled from public.shop_user u where u.email=?")
                .authoritiesByUsernameQuery("select u.email, u.role, enabled from public.shop_user u where u.email=?")
                .dataSource(jdbcTemplate.getDataSource())
                .passwordEncoder(bCryptPasswordEncoder);
    }
}