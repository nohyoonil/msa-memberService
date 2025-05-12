package org.yoon.msamemberservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.msamemberservice.Member;
import org.yoon.msamemberservice.MemberRepository;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;

    @Transactional
    @KafkaListener(topics = "validate.question", groupId = "member-service")
    public void listen(String message) {
        try {
            HashMap<String, Object> map = objectMapper.readValue(message, new TypeReference<>() {});
            Long voterId = Long.parseLong(map.get("voterId").toString());
            Long targetId = Long.parseLong(map.get("targetId").toString());
            Long reward = Long.parseLong(map.get("reward").toString());

            Member voter = memberRepository.findById(voterId)
                    .orElseThrow(() -> new RuntimeException("voter not found: " + voterId));
            Member target = memberRepository.findById(targetId)
                    .orElseThrow(() -> new RuntimeException("target not found: " + targetId));

            voter.plusPoints(reward);
            voter.plusVoteSum();
            target.plusVotedSum();

        } catch (JsonProcessingException | RuntimeException e) {
            e.printStackTrace(); // logger 사용 추천
        }
    }
}
