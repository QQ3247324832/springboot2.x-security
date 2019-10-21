package com.it.config;

import com.it.dao.UserRepository;
import com.it.domain.User;
import com.it.security.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception { //配置策略
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/static/**").permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll().successHandler(loginSuccessHandler())
                .and()
                .logout().permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .rememberMe()
                .and().sessionManagement().maximumSessions(10).expiredUrl("/login");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Bean
    public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        return new TokenBasedRememberMeServices("springRocks", userDetailsService());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder(4);
    }


    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return (httpServletRequest, httpServletResponse, authentication) -> {
            try {
                SecurityUser user = (SecurityUser) authentication.getPrincipal();
                log.info("USER : {} LOGOUT SUCCESS ! ", user.getUsername());
            } catch (Exception e) {
                log.error("printStackTrace", e);
            }
            httpServletResponse.sendRedirect("/login");
        };
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { //登入处理
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                User userDetails = (User) authentication.getPrincipal();
                log.info("USER : {} LOGIN SUCCESS !  ", userDetails.getUsername());
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }


    @Bean
    @Override
    public UserDetailsService userDetailsService() {    //用户登录实现
        return new UserDetailsService() {
            @Autowired
            private UserRepository userRepository;

            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(s);
                if (user == null) throw new UsernameNotFoundException("Username " + s + " not found");
                return new SecurityUser(user);
            }
        };
    }


}
