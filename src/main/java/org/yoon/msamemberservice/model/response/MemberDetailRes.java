package org.yoon.msamemberservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberDetailRes {

    private long id;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
}
