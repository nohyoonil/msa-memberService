package org.yoon.msamemberservice.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInForm {

    private String email;
    private String password;
}
