package com.lush.core.confing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
  /**
   * Define redis host name.
   */
  @Value("${spring.redis.host}")
  private String redisHost;
  /**
   * Define redis port.
   */
  @Value("${spring.redis.port}")
  private int redisPort;

  /**
   * Method name : jedisConnectionFactory. Description : Create a JedisConnectionFactory to set the
   * host, port, and pool for the redis.
   *
   * @return JedisConnectionFactory
   */
  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setHostName(redisHost);
    jedisConnectionFactory.setPort(redisPort);
    jedisConnectionFactory.setUsePool(true);
    return jedisConnectionFactory;
  }

  /**
   * Method name : redisTemplate. Description : Create a RedisTemplate to serialize keys and values.
   *
   * @param jedisConnectionFactory
   * @return RedisTemplate<String, Object>
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    redisTemplate.setConnectionFactory(jedisConnectionFactory);
    return redisTemplate;
  }
}
