package com.example.demopro.filter;

import com.alibaba.fastjson.JSON;
import com.example.demopro.service.Impl.UserDetailsServiceImpl;
import com.example.demopro.service.UserRolesService;
import com.example.demopro.service.UserService;
import com.example.demopro.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.*;

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

        if (servletRequest instanceof HttpServletRequest) {
            HttpServletResponse response = ((HttpServletResponse) servletResponse);  //保存response
            HttpServletRequest request = (HttpServletRequest) servletRequest;  //保存request

            //拦截/login和/register的请求，判断验证码,验证码正确则放行，否则，拦截
            if (request.getRequestURI().equals("/login") || request.getRequestURI().equals("/register")) {
                System.out.println("Filter拦截的URI为" + request.getRequestURI());
                String captcha = request.getParameter("captcha");
                Jedis jedis = new Jedis("47.107.157.20", 6379);
                jedis.auth("123456");
                String key_uuid = request.getParameter("uuid");
                String captcha_s = jedis.get("capt_key_" + key_uuid);

                //如果验证码一致，则放行，验证码不区分大小写，因此这里统一转换为小写来判断
                if (captcha.toLowerCase().equals(captcha_s.toLowerCase())) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                } else {
                    map.put("msg", "请求失败");
                    map.put("status", false);
                    map.put("reason", "验证码不正确");
                    String json_str = JSON.toJSONString(map);  //将map转换为JSON字符串
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().print(json_str);
                    System.out.println("验证码不正确");
                    return;
                }
            }
            //如果是验证码请求，一律让过，/captcha是验证码
            if (request.getRequestURI().equals("/captcha")) {
//                System.out.println("Filter拦截的URI为" + request.getRequestURI());
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            //获取Http请求中的Header中的token部分的内容
            String token = ((HttpServletRequest) servletRequest).getHeader("token");

            if (token != null && !(token.equals(""))) { //如果token不为空

                try {   //如果认证成功，放行，如果认证失败，直接过滤掉？
                    Claims claims = JwtUtils.VerifyJwt(token);
                    String username = (String) claims.get("username");
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
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().print(json_str);  //写进响应体
                    return;
                }

            } else {
                map.put("msg", "请求失败");
                map.put("status", false);
                map.put("reason", "未携带token");

                String json_str = JSON.toJSONString(map);  //将map转换为JSON字符串

                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(json_str);  //写进响应体
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);   //过滤
        return;
    }
}
