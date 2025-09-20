package com.example.restaurant.config.securityconfig.filters;

import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.token.TokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private TokenHandler tokenHandler;

    @Autowired
    public AuthFilter(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        token = token.substring(7);

        AccountDto accountDto = tokenHandler.validateToken(token);
        if (Objects.isNull(accountDto)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        List<SimpleGrantedAuthority> roles = getAuthorities(accountDto);
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Roles;" + accountDto.getRoles());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(accountDto, accountDto.getPassword(), roles);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }

    private List<SimpleGrantedAuthority> getAuthorities(AccountDto accountDto) {
    return accountDto.getRoles().stream().map(
            roleDto -> new SimpleGrantedAuthority("ROLE_" + roleDto.getCode().name())

    ).collect(Collectors.toList());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().contains("auth") ||
                request.getRequestURI().contains("swagger-ui") ||
                request.getRequestURI().contains("v3");

        }

}
