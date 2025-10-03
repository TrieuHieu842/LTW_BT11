package vn.iotstar.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.iotstar.Entity.*;
import vn.iotstar.Repository.*;

@Service
public record UserService(UserInfoRepository repository, PasswordEncoder passwordEncoder) {

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User added successfully!";
    }
}
