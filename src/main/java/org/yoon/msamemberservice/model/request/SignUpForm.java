package org.yoon.msamemberservice.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpForm {

    private String email;
    private String nickname;
    private String password;
}
