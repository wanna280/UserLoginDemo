package com.example.demopro.config;

import com.example.demopro.filter.AuthenticationTokenFilter;
import com.example.demopro.service.Impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity  //使用EnableWebSecurity注解，引入SpringSecurity的安全支持
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationTokenFilter authenticationTokenFilter;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //登录拦截

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()  //允许任意访问login页面
                .antMatchers("/test").hasAnyAuthority("user","admin")  //必须要有admin权限才能访问
                .antMatchers("/home").hasAnyAuthority("admin")  //登录之后可以访问home页面
                .anyRequest().authenticated();

        http.formLogin()  //使用表单登录
                .loginPage("/login")  //设置登录页面
                .loginProcessingUrl("/login")  //设置登录处理的API
                .successForwardUrl("/success")  //设置登录成功跳转的页面
                .failureForwardUrl("/failed");  //设置登录失败跳转的页面

        http.cors();  //允许跨域
        http.csrf().disable();  //关闭跨站拦截


        //不使用SESSION，这样就不会生成COOKIE，我们需要使用TOKEN去鉴权
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

        //这里必须使用注入的方式传入第一个参数，不然不行
        //如果不加http.addFilterBefore()方法，似乎不能设定为登录状态，但是其他能运行？？？？？？WTF
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //设置用户认证
        /**
         * 在内存中配置两个用户wanna、baby
         * wanna的角色是admin、baby的角色是user
         * wanna的密码为123456、baby的密码是111111
         */
//        auth.inMemoryAuthentication()    //在内存中配置用户
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("wanna")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("admin")
//                .authorities("admin")
//                .and()
//                .withUser("baby")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("user")
//                .authorities("user");

        //从数据库中加载用户信息
        //SpringSecurity规定，用户密码必须进行加密
        try{
            auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        }catch (Exception ex){

        }



    }

    @Override
    public void configure(WebSecurity web) throws Exception {  //设置拦截
        super.configure(web);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
