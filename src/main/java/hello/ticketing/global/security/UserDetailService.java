package hello.ticketing.global.security;

import hello.ticketing.domain.User;
import hello.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    //Spring Security가 로그인할 때 사용할 사용자 정보를 DB에서 찾아오는 클래스
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return MyUserDetails.builder()
                .user(user)
                .build();
    }
}
