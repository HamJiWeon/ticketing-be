package hello.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider", "provider_id"})
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private int age;

    @Column(name = "phone")
    private String phone;

    @Column(name = "provider", nullable = false)
    private String provider; // local, google, kakao, naver

    @Column(name = "provider_id", nullable = false)
    private String providerId; // 소셜 제공자의 고유 ID

    @Column(name = "address")
    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    @Builder
    public User(String name, String email, String password, int age, String phone, String address,
                String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.provider = provider == null ? "local" : provider;
        this.providerId = providerId == null ? email : providerId;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String password, String phone, String address) {
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }


}