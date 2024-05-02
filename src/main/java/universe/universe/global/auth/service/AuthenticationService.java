package universe.universe.global.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import universe.universe.global.auth.PrincipalDetails;
import universe.universe.domain.user.entity.User;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.reponse.ErrorCode;

@Service
public class AuthenticationService {
    public String getCurrentAuthenticatedUserEmail() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof PrincipalDetails) {
                return ((PrincipalDetails) principal).getEmail();
            }
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        throw new CustomException(ErrorCode.ACCESS_DENIED);
    }

    public User getCurrentAuthenticatedUser() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof PrincipalDetails) {
                return ((PrincipalDetails) principal).getUser();
            }
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        throw new CustomException(ErrorCode.ACCESS_DENIED);
    }
}
