package org.yoon.msamemberservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yoon.msamemberservice.model.request.SignUpForm;
import org.yoon.msamemberservice.model.response.MemberDetailRes;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<MemberDetailRes> getMember(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(memberService.signUp(form));
    }
}
