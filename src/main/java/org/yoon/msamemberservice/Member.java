package org.yoon.msamemberservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yoon.msamemberservice.model.response.MemberDetailRes;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nickname;
    private String password;
    private LocalDateTime createdAt;

    public static MemberDetailRes to(Member member) {
        return new MemberDetailRes(member.getId(), member.email,
                member.nickname, LocalDateTime.now());
    }
}
