//package com.example.ai_social_assistant.service.impl;
//
//import com.example.ai_social_assistant.service.RedisCommonService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//
//@Service
//@RequiredArgsConstructor
//public class RedisCommonServiceImpl implements RedisCommonService {
//
//    private final StringRedisTemplate redisTemplate;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    /**
//     * Lưu đối tượng dưới dạng JSON với thời gian sống (TTL).
//     *
//     * @param key        Khóa của Redis
//     * @param value      Đối tượng cần lưu
//     * @param ttlSeconds Thời gian sống của key, đơn vị giây
//     */
//    @Override
//    public void set(String key, Object value, long ttlSeconds) {
//        try {
//            // Chuyển đối tượng sang chuỗi JSON
//            String jsonValue = objectMapper.writeValueAsString(value);
//            redisTemplate.opsForValue().set(key, jsonValue, Duration.ofSeconds(ttlSeconds));
//        } catch (Exception e) {
//            throw new RuntimeException("Lỗi chuyển đổi đối tượng sang JSON", e);
//        }
//    }
//
//    /**
//     * Lấy đối tượng từ Redis và chuyển đổi chuỗi JSON thành đối tượng kiểu T.
//     *
//     * @param key   Khóa cần truy vấn
//     * @param clazz Lớp của đối tượng cần lấy
//     * @param <T>   Kiểu đối tượng cần lấy
//     * @return Đối tượng kiểu T, trả về null nếu key không tồn tại
//     */
//    @Override
//    public <T> T get(String key, Class<T> clazz) {
//        String jsonValue = redisTemplate.opsForValue().get(key);
//        if (jsonValue == null) {
//            return null;
//        }
//        try {
//            return objectMapper.readValue(jsonValue, clazz);
//        } catch (Exception e) {
//            throw new RuntimeException("Lỗi chuyển đổi chuỗi JSON về đối tượng", e);
//        }
//    }
//
//    @Override
//    public <T> T get(String key, TypeReference<T> typeReference) {
//        String json = redisTemplate.opsForValue().get(key);
//        if (json == null) {
//            return null;
//        }
//        try {
//            return objectMapper.readValue(json, typeReference);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to deserialize Redis value for key: " + key, e);
//        }
//    }
//
//    /**
//     * Xóa key khỏi Redis.
//     *
//     * @param key Khóa cần xóa
//     * @return true nếu xóa thành công, false nếu không tìm thấy key
//     */
//    @Override
//    public boolean delete(String key) {
//        Boolean result = redisTemplate.delete(key);
//        return result;
//    }
//}
