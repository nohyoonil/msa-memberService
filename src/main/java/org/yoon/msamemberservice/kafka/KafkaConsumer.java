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
import org.yoon.msamemberservice.model.dto.VoteDetailDto;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final KafkaProducer kafkaProducer;
    private final MemberRepository memberRepository;

    @Transactional
    @KafkaListener(topics = "validate.member", groupId = "member-service")
    public void listen(String message) {
        try {
            VoteDetailDto voteDetailDto = objectMapper.readValue(message, VoteDetailDto.class);
            Long voterId = voteDetailDto.getVoterId();
            Long targetId = voteDetailDto.getTargetId();
            int reward = voteDetailDto.getRewards();

            Member voter = memberRepository.findById(voterId)
                    .orElseThrow(() -> new RuntimeException("voter not found: " + voterId));
            Member target = memberRepository.findById(targetId)
                    .orElseThrow(() -> new RuntimeException("target not found: " + targetId));

            voter.plusPoints(reward);
            voter.plusVoteSum();
            target.plusVotedSum();

            kafkaProducer.send("create.notification", objectMapper.writeValueAsString(voteDetailDto));

        } catch (JsonProcessingException | RuntimeException e) {
            e.printStackTrace(); // logger 사용 추천
        }
    }
}
