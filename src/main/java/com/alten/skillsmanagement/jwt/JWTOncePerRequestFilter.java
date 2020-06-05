package com.alten.skillsmanagement.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTOncePerRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.addHeader("Access-Control-Allow-Headers", "Origin, " +
                "Accept, " +
                "X-Requested-With, " +
                "Content-Type, " +
                "Access-Control-Request-Method, " +
                "Access-Control-Request-Headers, " +
                "Authorization");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, " +
                "Access-Control-Allow-Credentials, " +
                "Authorization");

        String authHeader = request.getHeader("Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {

            if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.replace("Bearer ", "");

            try {
                String key = "altenaltenaltenaltenaltenaltenaltenaltenaltenaltenaltenalten";

                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                        .build()
                        .parseClaimsJws(token);

                Claims body = claimsJws.getBody();
                String username = body.getSubject();
                List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                        .map(item -> new SimpleGrantedAuthority(item.get("authority")))
                        .collect(Collectors.toSet());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        simpleGrantedAuthorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                throw new IllegalStateException(String.format("Token %s can not be trusted", token));
            }

            filterChain.doFilter(request, response);

        }

    }

}
