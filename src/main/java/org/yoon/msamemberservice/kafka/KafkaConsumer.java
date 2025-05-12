package org.yoon.msamemberservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "vote.created", groupId = "member-service")
    public void listen(String message) {
        System.out.println(message);
    }
}
