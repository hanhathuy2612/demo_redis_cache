package com.example.redis_java_spring_boot_demo.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {

        log.info("Info -> Redis Cache Configuration");

        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(2))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        cacheConfig.usePrefix();
        cacheConfig.prefixCacheNameWith("id");
        log.info("Duration -> {} seconds", cacheConfig.getTtl().getSeconds());

        return cacheConfig;
    }

    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory,
                                               RedisCacheConfiguration redisCacheConfiguration) {
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean
    public RedisCacheWriter redisCacheWriter(RedisConnectionFactory connectionFactory) {
        return RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
    }

    @Bean
    public TimeRedisCache redisCache(RedisCacheWriter cacheWriter,
                                     RedisCacheConfiguration configuration,
                                     Environment environment) {
        return new TimeRedisCache(environment.getProperty("spring.application.name"), cacheWriter, configuration);
    }

}
