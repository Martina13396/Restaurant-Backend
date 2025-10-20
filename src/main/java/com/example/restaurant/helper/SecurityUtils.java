package com.example.restaurant.helper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

        public static boolean isAdmin() {
            return SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"));
        }

    public static boolean resolveIncludeInactive(boolean includeInactive) {
        return isAdmin() && includeInactive;
    }
}
