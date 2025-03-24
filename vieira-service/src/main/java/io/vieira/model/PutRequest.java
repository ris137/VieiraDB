package io.vieira.model;

public class PutRequest {
    private final Object key;
    private final Object value;

    public PutRequest(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
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
