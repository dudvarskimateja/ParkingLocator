package com.example.Location.Demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import com.example.Location.Demo.Model.Message;
import com.example.Location.Demo.Redis.RedisMessagePublisher;
import com.example.Location.Demo.Redis.RedisMessageSubscriber;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;

    @Autowired
    private RedisMessageSubscriber redisMessageSubscriber;

    private static final String REDIS_CHANNEL = "myapp";

    @Override
    public void sendMessage(Message message) {
        redisMessagePublisher.publish(message);
    }

    @Override
    public List<Message> getAllMessages() {
        List<Object> messages = redisTemplate.opsForList().range(REDIS_CHANNEL, 0, -1);
        return messages.stream().map(obj -> (Message) obj).collect(Collectors.toList());
    }

    @Override
    public void clearAllMessages() {
        redisTemplate.delete(REDIS_CHANNEL);
    }

    @Autowired
private JedisPool jedisPool;

private JedisPubSub jedisPubSub;

    @Override
    public void startListening() {
        jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                // Handle received message
            }
        };
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.subscribe(jedisPubSub, REDIS_CHANNEL);
        }
    }
    
    @Override
    public void stopListening() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedisPubSub.unsubscribe();
        }
    }
}