package com.example.sbb.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPasswordUpdateForm {
    @NotEmpty(message = "현재 비밀번호를 입력해주십시오.")
    private String currentPassword;

    @NotEmpty(message = "새 비밀번호를 입력해주십시오.")
    private String newPassword;

    @NotEmpty(message = "새 비밀번호 확인을 입력해주십시오.")
    private String newPassword2;
}
