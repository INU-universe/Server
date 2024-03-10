package universe.universe.global.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import universe.universe.global.auth.PrincipalDetails;
import universe.universe.domain.user.entity.User;

@Service
public class AuthenticationService {
    public String getCurrentAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof PrincipalDetails) {
                return ((PrincipalDetails) principal).getEmail();
            }
            return null; // 또는 "anonymousUser"와 같은 기본값
        }
        return null;
    }

    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof PrincipalDetails) {
                return ((PrincipalDetails) principal).getUser();
            }
            return null; // 또는 "anonymousUser"와 같은 기본값
        }
        return null;
    }
}
