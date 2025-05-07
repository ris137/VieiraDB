package io.vieira.model;

public class PutResponse {
    private Object key;
    private Object valueBefore;
    private Object valueAfter;

    public PutResponse(Object key, Object valueBefore, Object valueAfter) {
        this.key = key;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
    }
}
