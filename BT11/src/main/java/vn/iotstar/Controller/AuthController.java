package vn.iotstar.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.Model.LoginDto;
import vn.iotstar.Model.*;
import vn.iotstar.Entity.Role;
import vn.iotstar.Entity.Users;
import vn.iotstar.Repository.RoleRepository;
import vn.iotstar.Repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Đăng nhập (Sign in)
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("✅ User signed in successfully!");
    }

    // ✅ Đăng ký (Sign up)
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        // Kiểm tra username tồn tại chưa
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("❌ Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // Kiểm tra email tồn tại chưa
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("❌ Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        // Tạo tài khoản mới
        Users user = new Users();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setEnabled(true);

        // Gán role mặc định "USER"
        Role role = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("USER");
                    return roleRepository.save(newRole);
                });

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return new ResponseEntity<>("✅ User registered successfully!", HttpStatus.OK);
    }
}
