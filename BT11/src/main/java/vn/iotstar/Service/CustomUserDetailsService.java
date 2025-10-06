package vn.iotstar.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.iotstar.Entity.Role;
import vn.iotstar.Entity.Users;
import vn.iotstar.Repository.UserRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // ✅ Constructor Injection (chuẩn trong Spring Boot 3)
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Cho phép đăng nhập bằng username hoặc email
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Users user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        // Trả về UserDetails gốc của Spring Security (hoặc bạn có thể trả về MyUserDetails tuỳ cách làm)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),                       // hoặc user.getUsername()
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    // ✅ Chuyển danh sách Role -> GrantedAuthority (Spring Security hiểu được)
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

