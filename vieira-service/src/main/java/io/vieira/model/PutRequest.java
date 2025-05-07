package io.vieira.model;

public record PutRequest(String key, Object value) {

    @Override
    public String toString() {
        return "PutRequest{" + "key=" + key + ", value=" + value + '}';
    }
}
