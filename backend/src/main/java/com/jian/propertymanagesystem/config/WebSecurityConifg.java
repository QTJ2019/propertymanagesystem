package com.jian.propertymanagesystem.config;

import com.jian.propertymanagesystem.intercepter.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import sun.security.util.Password;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: qtj
 * @Date: 2020/12/25 10:10
 * @Version
 */
@Configuration
//下面的注解使得SpringMVC集成了Spring Security的web安全支持
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true , securedEnabled = true)  //这里要配置，才能在接口上使用@preAuthorize,@postAuthorize,@Secured
public class WebSecurityConifg extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .authorizeRequests()//配置路径拦截
                 .antMatchers("/","/home","/baseinformationadmin/**").permitAll()
                 //.anyRequest().authenticated()//所有的请求必须认证通过
                 .and()
                .formLogin() //对表单认证相关的配置
                 .loginPage("http://localhost:8080/login")
                 .loginProcessingUrl("/process") //实际表单向后台提交用户信息的Action，再由过滤器UsernamePasswordAuthenticationFilter拦截处理
                 .successForwardUrl("/login/success") //认证成功后会跳转到此
                 //.failureHandler(new AjaxAuthFailHandler())
                 .failureForwardUrl("/login/fail")//登录失败后会调用的接口
                 .permitAll()
                 .and()
                .logout()  //对注销相关的配置
//                 .logoutUrl("")//自定义推出的url
                 .logoutSuccessUrl("/logout?success") //设置成功推出后，跳转到的页面
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())//设置了该项，logoutSuccessUrl这项会取消
                 .permitAll()
                 .and()
                .sessionManagement()//设置会话
                .invalidSessionUrl("http://localhost:8080/login")  //这里可以设置当传入的sessionid无效时，会跳转到的路径
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);


    }

    /*
    发生异常时，状态码为401（不做这个配置时发生异常的状态码是302）
     */
    public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
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
