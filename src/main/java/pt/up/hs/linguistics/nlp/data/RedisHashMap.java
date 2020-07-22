package pt.up.hs.linguistics.nlp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;

import java.util.Map;

public class RedisHashMap<T> {
    private final Logger log = LoggerFactory.getLogger(RedisHashMap.class);

    private final HashOperations<String, String, T> hashOperations;
    private final String id;

    public RedisHashMap(HashOperations<String, String, T> hashOperations, String id) {
        this.hashOperations = hashOperations;
        this.id = id;
    }

    public void save(String key, T obj) {
        hashOperations.put(id, key, obj);
        log.info(String.format("Object with key '%s' saved", key));
    }

    public T get(String key) {
        return (T) hashOperations.get(id, key);
    }

    public Map<String, T> getAll(){
        return hashOperations.entries(id);
    }

    public void delete(String key) {
        hashOperations.delete(id, key);
        log.info(String.format("Object with key '%s' deleted", key));
    }
}
