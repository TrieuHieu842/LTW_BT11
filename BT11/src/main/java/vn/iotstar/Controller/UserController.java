package vn.iotstar.Controller;

import vn.iotstar.Entity.UserInfo;
import vn.iotstar.Service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/new")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }
}
