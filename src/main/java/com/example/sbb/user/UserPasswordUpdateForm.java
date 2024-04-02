package com.example.sbb.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserPasswordUpdateForm {
    @NotBlank(message = "현재 비밀번호를 입력해주십시오.")
    private String currentPassword;

    @NotBlank(message = "새  비밀번호를 입력해주십시오.")
    private String newPassword;

    @NotBlank(message = "새 비밀번호 확인을 입력해주십시오.")
    private String newPassword2;
}
