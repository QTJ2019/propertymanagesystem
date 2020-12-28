package com.jian.propertymanagesystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: qtj
 * @Date: 2020/12/25 10:10
 * @Version
 */
@Configuration
//下面的注解使得SpringMVC集成了Spring Security的web安全支持
@EnableWebSecurity
public class WebSecurityConifg extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()//配置路径拦截
                 .antMatchers("/","/home").permitAll()
                 //.anyRequest().authenticated()//所有的请求必须认证通过
                 .and()
                .formLogin() //对表单认证相关的配置
                 //.loginPage("/login")
                 .loginProcessingUrl("/process") //实际表单向后台提交用户信息的Action，再由过滤器UsernamePasswordAuthenticationFilter拦截处理
                 .successForwardUrl("/login/success") //认证成功后会跳转到此
                 .failureForwardUrl("/login/fail")//登录失败后会调用的接口
                 .permitAll()
                 .and()
                .logout()  //对注销相关的配置
//                 .logoutUrl("")//自定义推出的url
                 .logoutSuccessUrl("/logout/success") //设置成功推出后，跳转到的页面
                 .permitAll()
                 .and()
                .sessionManagement()//设置会话
                //.invalidSessionUrl("")  //这里可以设置当传入的sessionid无效时，会跳转到的路径
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);


    }

     /*
     密码编码器，数据库的密码都是经过加密的，我们需要先对密码才能进行比对
      */
     @Bean
     public PasswordEncoder passwordEncoder(){
        // return new BCryptPasswordEncoder();
         return NoOpPasswordEncoder.getInstance();
     }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("admin").password("admin").roles("USER");
//    }
}
