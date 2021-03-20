package com.jian.propertymanagesystem.config;

import com.jian.propertymanagesystem.util.RSAEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * @Author: qtj
 * @Date: 2021/2/23 16:59
 * @Version
 */
@Component
public class CustomUserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public static CustomUserAuthenticationProvider customUserAuthenticationProvider;

    @PostConstruct
    public void init(){
        customUserAuthenticationProvider = this;
        customUserAuthenticationProvider.userDetailsService = this.userDetailsService;
        customUserAuthenticationProvider.passwordEncoder = this.passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        password = password.replace(' ','+');
        String decodePassword =  null;
        try {
            decodePassword = RSAEncryptUtil.decrypt(password);
        } catch (Exception e) {
            throw new CredentialsExpiredException("密码解密错误");
        }
        //获取用户信息
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (user == null){
            throw new CredentialsExpiredException("没有该用户");
        }

        if (!passwordEncoder.matches(decodePassword,user.getPassword())){
            throw  new CredentialsExpiredException("密码不一致");
        }

        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user,password,authorities);
    }

    /**
     * 如果该AuthenticationProvider支持传入的Authentication对象，则返回true
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
