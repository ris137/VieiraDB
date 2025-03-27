package io.vieira.model;

public class PutRequest {
    private final String key;
    private final Object value;

    public PutRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PutRequest{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
