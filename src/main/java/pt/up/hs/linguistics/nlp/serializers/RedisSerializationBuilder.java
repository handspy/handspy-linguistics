package pt.up.hs.linguistics.nlp.serializers;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisSerializationBuilder {

    public static <T> RedisTemplate<String, T> getSnappyRedisTemplate(
        final LettuceConnectionFactory factory,
        final Class<T> clazz
    ) {
        SnappyMsgPackRedisSerializer<T> snappyMsgPackSerializer = new SnappyMsgPackRedisSerializer<>(clazz);

        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setDefaultSerializer(snappyMsgPackSerializer);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(snappyMsgPackSerializer);
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(snappyMsgPackSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    public static <T> RedisTemplate<String, T> getMsgPackRedisTemplate(
        final LettuceConnectionFactory factory,
        final Class<T> clazz) {
        MsgPackRedisSerializer<T> msgPackRedisSerializer = new MsgPackRedisSerializer<>(clazz);

        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setDefaultSerializer(msgPackRedisSerializer);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(msgPackRedisSerializer);
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(msgPackRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
