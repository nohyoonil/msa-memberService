package org.yoon.msamemberservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yoon.msamemberservice.model.request.SignUpForm;
import org.yoon.msamemberservice.model.response.MemberDetailRes;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDetailRes signUp(SignUpForm form) {
        Member member = memberRepository.save(Member.builder()
                .email(form.getEmail())
                .nickname(form.getNickname())
                .password(form.getPassword())
                .build());

        return Member.to(member);
    }
}
