package com.gnw.config;

import com.gnw.handler.MyAccessDenieHandler;
import com.gnw.handler.MyAuthenticationEntryPoint;
import com.gnw.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAccessDenieHandler myAccessDenieHandler;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login");//默认login下跳转到登录页面，未设置自定义登录页面则跳转默认登录页面。不设置login则会报403
        //http.authorizeRequests().anyRequest().authenticated();//所有页面都需要先登录
//        http.exceptionHandling()
//                .accessDeniedHandler(myAccessDenieHandler)//无权限返回403
//                .authenticationEntryPoint(myAuthenticationEntryPoint);//未登录返回
        http.rememberMe()
                .tokenValiditySeconds(1800)
                .userDetailsService(userDetailsServiceImpl)
                .tokenRepository(getPersistentTokenRepository());
        /*防止同一账号同时登录多次
        * 两种方法去 前一个为踢掉之前的登录  后一个为防止再次登录  都需要配置http.logout.logoutSuccessUrl("/home").invalidateHttpSession(true);*/
//        http.sessionManagement()
//                .maximumSessions(1).expiredUrl("/login");
//        http.sessionManagement()f
//                .maximumSessions(1).maxSessionsPreventsLogin(true);
//        http.logout()
//                .logoutSuccessUrl("/home")
//                .invalidateHttpSession(true);
    }
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl impl = new JdbcTokenRepositoryImpl();
        impl.setDataSource(dataSource);
        //在项目启动时帮助创建存储rememberme表，
        //第二次启动时注释此代码,否则会报错
        //impl.setCreateTableOnStartup(true);
        return impl;
    }
    @Bean
    public PasswordEncoder getPPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
