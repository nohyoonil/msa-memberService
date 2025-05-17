package org.yoon.msamemberservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.msamemberservice.Member;
import org.yoon.msamemberservice.MemberRepository;
import org.yoon.msamemberservice.model.dto.VoteDetailDto;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final MemberRepository memberRepository;

    @Transactional
    @KafkaListener(topics = "validate.memberId", groupId = "member-service")
    public void listen(String message) {
        try {
            Long memberId = objectMapper.readValue(message, Long.class);
            memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("target not found: " + memberId));

            redisTemplate.opsForValue().set("memberId:" + memberId, "exist", Duration.ofSeconds(3));
        } catch (JsonProcessingException | RuntimeException e) {
            e.printStackTrace();
        }
    }
}
