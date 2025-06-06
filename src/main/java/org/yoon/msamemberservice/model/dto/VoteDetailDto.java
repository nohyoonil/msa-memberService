package org.yoon.msamemberservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteDetailDto {

    private long voteId;
    private long voterId;
    private long targetId;
    private long questionId;
    private int rewards;
    private boolean opened;
    private LocalDateTime createdAt;
}
