package farmSystem.closeUp.config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtils {

    private static List<SimpleGrantedAuthority> notUserAuthority = new ArrayList<>();

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationServiceException("사용자를 찾지 못함");
        }

        if (authentication.isAuthenticated()
                && !CollectionUtils.containsAny(
                authentication.getAuthorities(), notUserAuthority)) {
            return Long.valueOf(authentication.getName());
        }

        return 0L;
    }
}