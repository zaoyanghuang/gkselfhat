package com.gnw.config;


import com.gnw.handler.MyAuthenticationEntryPoint;
import com.gnw.handler.MyAccessDenieHandler;
import com.gnw.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
        //403权限不足  自定义异常处理
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDenieHandler) //无权限返回403或者其他
                .authenticationEntryPoint(myAuthenticationEntryPoint);//账号未登录 返回状态
        //登出
        http.logout()
                .logoutUrl("/url/logout") //支持自定义，但不建议有可能出现框架其他模块失效
                .logoutSuccessUrl("/login.jsp");//登出成功后跳转

        //表单认证
        http.formLogin()
                .usernameParameter("admin") //修改默认登录账号密码
                .passwordParameter("123456");
        //关闭csrf防护
        http.csrf().disable();
        //免登陆  html页面配置记住我的字段,字段值必须为 remember-me
        http.rememberMe()
                .tokenValiditySeconds(1800)//默认两周 秒
                .userDetailsService(userDetailsServiceImpl)//用户登录逻辑对象
                .tokenRepository(getPersistentTokenRepository());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    //使用上面repository 需要注入对象PersistentTokenRepository
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
//        repository.setCreateTableOnStartup(true);
        repository.setDataSource(dataSource);
        return repository;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
