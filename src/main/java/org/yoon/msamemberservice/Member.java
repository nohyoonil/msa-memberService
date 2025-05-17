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
    private int points;
    private long voteSum;
    private long votedSum;
    private LocalDateTime createdAt;

    public static MemberDetailRes to(Member member) {
        return MemberDetailRes.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .points(member.getPoints())
                .voteSum(member.getVoteSum())
                .votedSum(member.getVotedSum())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public int plusPoints(int reward) {
        return this.points += reward;
    }

    public long plusVoteSum() {
        return this.voteSum += 1;
    }

    public long plusVotedSum() {
        return this.votedSum += 1;
    }

    public void usePoints(int points) {
        this.points -= points;
    }
}
