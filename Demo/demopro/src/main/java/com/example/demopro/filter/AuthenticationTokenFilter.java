package com.example.demopro.filter;

import com.alibaba.fastjson.JSON;
import com.example.demopro.entity.UserBean;
import com.example.demopro.entity.UserRolesBean;
import com.example.demopro.security.TokenAuthentication;
import com.example.demopro.service.Impl.UserDetailsServiceImpl;
import com.example.demopro.service.UserRolesService;
import com.example.demopro.service.UserService;
import com.example.demopro.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现过滤器接口，用于认证Token
 * 用于处理收到的Token并spring-security上下文的生成注入Authentication实例
 */
@WebFilter(urlPatterns = "/**")
@Configuration
public class AuthenticationTokenFilter implements Filter {
    @Autowired
    UserService userService;
    @Autowired
    UserRolesService userRolesService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>();   //保存当token校验失败时，返回的map
        HttpServletResponseWrapper req = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);

        //如果ServletRequest是HttpServletRequest实例
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletResponse response = ((HttpServletResponse) servletResponse);  //保存response
            HttpServletRequest request = (HttpServletRequest) servletRequest;  //保存request

            //System.out.println(request.getRequestURI());
            //如果是登录请求，一律让过
            //  /login是本地DevServer的路径，/api/login是远端服务器的地址
            if(request.getRequestURI().equals("/login") || request.getRequestURI().equals("/api/login")){
                System.out.println("Filter-"+request.getRequestURI());
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
            //获取Http请求中的Header中的token部分的内容
            String token = ((HttpServletRequest) servletRequest).getHeader("token");
            //System.out.println(token);  //打印获取到的token
            if (token != null && !"".equals(token)) { //如果token不为空

                try {   //如果认证成功，放行，如果认证失败，直接过滤掉？
                    //System.out.println(token);
                    Claims claims = JwtUtil.VerifyJwt(token);
                    //System.out.println(token);
                    String username = (String) claims.get("username");
                    //System.out.println(token);
                    //System.out.println("用户" + username + "认证成功");

                    //如果得到了token，对token进行解析，并从已经认证的用户列表中根据username进行查询
                    //如果获取对应的权限信息，并在上下文中设置其权限
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    //System.out.println(userDetails.getAuthorities());
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    //System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                } catch (Exception exception) {
                    System.out.println("Token校验失败");
                    map.put("msg", "请求失败");
                    map.put("status", false);
                    map.put("reason", "token校验不正确");
                    String json_str = JSON.toJSONString(map);  //将map转换未JSON字符串

                    //System.out.println("未携带token，认证失败");
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().print(json_str);

                    return;
                }

            } else {
                map.put("msg", "请求失败");
                map.put("status", false);
                map.put("reason", "未携带token");

                String json_str = JSON.toJSONString(map);  //将map转换为JSON字符串

                //System.out.println("未携带token，认证失败");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(json_str);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);   //过滤
    }
}
