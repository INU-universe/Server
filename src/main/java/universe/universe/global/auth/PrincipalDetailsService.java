package universe.universe.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUserEmail(userEmail);
        return new PrincipalDetails(userEntity);
    }
}
