package com.dream.exerciseSystem.filter;

import com.dream.exerciseSystem.domain.LoginStudentDetails;
import com.dream.exerciseSystem.utils.JwtUtil;
import com.dream.exerciseSystem.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 对请求放行
             filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        String studentId;
        try {
            Claims claims = JwtUtil.parsedResult(token);
            studentId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        // 从redis中获取用户信息
        String redisKey = "student:login:" + studentId;
        Object loginStudentDetailsFromRedis = redisCache.getCacheObject(redisKey);

        if (Objects.isNull(loginStudentDetailsFromRedis)){
            throw new RuntimeException("用户未登录");
        }

        // 存入SecurityContextHolder


        // TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginStudentDetailsFromRedis, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
