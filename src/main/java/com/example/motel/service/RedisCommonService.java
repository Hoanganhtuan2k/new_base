package com.example.motel.service;

import com.fasterxml.jackson.core.type.TypeReference;

public interface RedisCommonService {

    void set(String key, Object value, long ttlSeconds);

    <T> T get(String key, Class<T> clazz);

    <T> T get(String key, TypeReference<T> typeReference);

    boolean delete(String key);
}
