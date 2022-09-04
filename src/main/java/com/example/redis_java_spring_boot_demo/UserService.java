package com.example.redis_java_spring_boot_demo;

import com.example.redis_java_spring_boot_demo.cache.TimeRedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TimeRedisCache redisCache;

    public void create(User user) {
        userRepository.save(user);
        redisCache.put(user.getId(), user);
    }

    public User findById(Integer id) {
        User user = redisCache.get(id, User.class);
        if (user == null) {
            user = userRepository.findById(id).get();
            redisCache.put(user.getId(), user);
        }
        return user;
    }
}
