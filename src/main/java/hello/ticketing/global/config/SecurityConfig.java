package hello.ticketing.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // spring.h2.console.enabled는 application-dev.properties에만 true로 들어있어서,
    // 이 값을 그대로 "지금 dev 프로필로 떠 있는지" 판단하는 신호로 재사용한다.
    @Value("${spring.h2.console.enabled:false}")
    private boolean h2ConsoleEnabled;

    // TODO: 로그인(인증) 기능 붙이면 permitAll 걷어내고 anyRequest().authenticated()로 복구
    // 지금은 로그인 API 자체가 없어서 authenticated()로 막아두면 아무 API도 테스트할 수 없음
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.POST, "/users").permitAll();
            if (h2ConsoleEnabled) {
                auth.requestMatchers("/h2-console/**").permitAll();
            }
            auth.anyRequest().permitAll();
        });

        // CSRF는 브라우저가 쿠키/세션을 자동으로 실어 보내는 걸 노리는 공격을 막기 위한 방어라서
        // 세션 기반 로그인(폼 로그인)에서나 의미가 있다. 이 프로젝트는 세션이 아니라
        // JWT 같은 토큰을 헤더에 직접 실어 보내는 stateless API로 갈 거라 CSRF 공격 자체가 성립하지 않는다.
        // 그래서 API 전체에 대해 CSRF를 끈다. (세션 로그인을 쓰게 되면 이 결정을 다시 검토해야 함)
        http.csrf(csrf -> csrf.disable());

        if (h2ConsoleEnabled) {
            http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        }

        return http.build();
    }

    //일부만 작성

    /* 비밀번호 암호화
     * BCrypt 해시 함수를 사용하여 비밀번호를 암호화
     *  */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
