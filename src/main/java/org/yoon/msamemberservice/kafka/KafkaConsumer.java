package org.yoon.msamemberservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.yoon.msamemberservice.Member;
import org.yoon.msamemberservice.MemberRepository;
import org.yoon.msamemberservice.model.dto.UsePointDto;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final int POINT = 100;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final MemberRepository memberRepository;

    @KafkaListener(topics = "validate.memberId", groupId = "member-service")
    public void validateMemberId(String message) {
        try {
            Long memberId = objectMapper.readValue(message, Long.class);
            memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("target not found: " + memberId));

            redisTemplate.opsForValue().set("memberId:" + memberId, "exist", Duration.ofSeconds(3));
        } catch (JsonProcessingException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "use.point", groupId = "member-service")
    public void usePoint(String message) {
        try {
            UsePointDto dto = objectMapper.readValue(message, UsePointDto.class);
            long memberId = dto.getMemberId();
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("target not found: " + memberId));

            if (member.getPoints() < POINT) throw new RuntimeException("not enough points");
            member.usePoints(POINT);
            memberRepository.save(member);

            redisTemplate.opsForValue()
                    .set("memberId:" + memberId + "voteId:" + dto.getVoteId() + "open",
                            "exist", Duration.ofSeconds(3));
        } catch (JsonProcessingException | RuntimeException e) {
            e.printStackTrace();
        }
    }
}
