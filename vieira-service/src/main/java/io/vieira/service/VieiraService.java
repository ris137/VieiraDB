package io.vieira.service;

import io.vieira.model.PutRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VieiraService {

    private static final Map<Object, Object> MAP = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(VieiraService.class);

    public Object get(Object key) {
        if (hasKey(key)) return MAP.get(key);
        return null;
    }

    public void put(PutRequest request) {
        put(request.key(), request.value());
    }

    public void put(Object key, Object value) {
        if (isValidKey(key) && isValidValue(value)) {
            if (MAP.containsKey(key)) logger.info("Key {} already exists, overwriting.", key);
            MAP.put(key, value);
            logger.info("Added {} -> {}", key, value);
        } else {
            logger.error("Invalid input: key: {} value:{}", key, value);
        }
    }

    public void remove(Object key) {
        if (hasKey(key)) {
            MAP.remove(key);
            logger.info("Removed {}", key);
        }
    }

    public boolean hasKey(Object key) {
        if (isValidKey(key) && MAP.containsKey(key)) return true;
        return false;
    }

    private boolean isValidKey(Object key) {
        if (null == key) return false;
        return true;
    }

    private boolean isValidValue(Object value) {
        if (null == value) return false;
        return true;
    }
}
