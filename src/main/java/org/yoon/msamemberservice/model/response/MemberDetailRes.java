package org.yoon.msamemberservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MemberDetailRes {

    private long id;
    private String email;
    private String nickname;
    private long points;
    private long voteSum;
    private long votedSum;
    private LocalDateTime createdAt;
}
