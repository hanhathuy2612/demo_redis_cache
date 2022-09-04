package com.example.redis_java_spring_boot_demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/v1/")
public class UserController {
    private final UserService userService;
    @PostMapping("info")
    public String create(@RequestBody User user) {
        userService.create(user);
        return user.getName();
    }

    @GetMapping("{id}/info")
    public User get(@PathVariable Integer id){
        return userService.findById(id);
    }
}
