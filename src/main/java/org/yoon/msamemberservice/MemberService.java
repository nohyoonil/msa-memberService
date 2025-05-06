package org.yoon.msamemberservice;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yoon.msamemberservice.model.request.SignInForm;
import org.yoon.msamemberservice.model.request.SignUpForm;
import org.yoon.msamemberservice.model.response.MemberDetailRes;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public MemberDetailRes signUp(SignUpForm form) {
        Member member = memberRepository.save(Member.builder()
                .email(form.getEmail())
                .nickname(form.getNickname())
                .password(encoder.encode(form.getPassword()))
                .build());

        return Member.to(member);
    }

    public String signIn(SignInForm form) {
        Member member = memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new RuntimeException("not exists"));

        if (!encoder.matches(form.getPassword(), member.getPassword()))
            throw new RuntimeException("not match");

        return "todo jwt";
    }
}
