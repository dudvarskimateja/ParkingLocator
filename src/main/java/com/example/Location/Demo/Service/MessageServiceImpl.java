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

    public MessageServiceImpl(RedisMessageSubscriber redisMessageSubscriber2) {
    }

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

private String redisHost = "127.0.0.1:6379";

private int redisPort = 6379;

private String redisChannel = "location"; 

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

    public void testRedisMessage() {
        // Create the Redis message subscriber
        RedisMessageSubscriber redisMessageSubscriber = new RedisMessageSubscriber(jedisPool);
    
        // Create the message service
        MessageService messageService = new MessageServiceImpl(redisMessageSubscriber);
    
        // Publish a message
        messageService.publish(new Message("test", "Hello, world!"));
    
        // Start listening for messages
        messageService.startListening();
    
        // Wait for a few seconds to receive the message
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        // Stop listening for messages
        messageService.stopListening();
    }

    @Override
    public void publish(Message message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'publish'");
    }
}