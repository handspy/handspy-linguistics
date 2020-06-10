package pt.up.hs.linguistics.nlp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import pt.up.hs.linguistics.config.ApplicationProperties;

/*@Configuration
@EnableRedisRepositories*/
public class NlpRedisConfiguration {

    /*@Bean*/
    public LettuceConnectionFactory redisConnectionFactory(
        ApplicationProperties appProperties
    ) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(appProperties.getNlpRedis().getHostname());
        config.setPort(appProperties.getNlpRedis().getPort());
        config.setPassword(RedisPassword.of(appProperties.getNlpRedis().getPassword()));

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(config);
        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }

    /*@Bean*/
    public RedisTemplate<?, ?> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
