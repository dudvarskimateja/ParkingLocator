package com.example.Location.Demo.Redis;


import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class RedisMessageSubscriber implements MessageListener {

    private JedisPool jedisPool;
    private Thread subscriptionThread;
    private boolean isSubscribed;

    public RedisMessageSubscriber(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        this.isSubscribed = false;
    }

    public void subscribe(String channel, JedisPubSub RedisMessageSubscriber) {
        if (!isSubscribed) {
            subscriptionThread = new Thread(() -> {
                try (Jedis jedis = jedisPool.getResource()) {
                    jedis.subscribe(RedisMessageSubscriber, channel);
                }
            });
            subscriptionThread.start();
            isSubscribed = true;
        }
    }

    public void unsubscribe() {
        if (isSubscribed) {
            subscriptionThread.interrupt();
            isSubscribed = false;
        }
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        System.out.println(String.format("Received message: %s from channel: %s", body, channel));
        // Handle received message
    }
}