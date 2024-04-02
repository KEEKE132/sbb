package com.example.sbb.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup_form";
        }
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
            bindingResult.rejectValue("password2","passwordIncorrect","비밀번호 확인이 일치하지 않습니다.");
            return "signup_form";
        }
        try{
            userService.create(userCreateForm.getUsername(),userCreateForm.getEmail(),userCreateForm.getPassword1());
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("signupFailed",e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "login_form";
    }

    @GetMapping("/passwordUpdate")
    public String passwordUpdate(UserPasswordUpdateForm userPasswordUpdateForm){return "passwordUpdate_form";}

    @PostMapping("passwordUpdate")
    public String passwordUpdate(@Valid UserPasswordUpdateForm userPasswordUpdateForm, BindingResult bindingResult, @AuthenticationPrincipal SiteUser siteUser){
        if(bindingResult.hasErrors()){
            return "passwordUpdate_form";
        }
        if(!userPasswordUpdateForm.getCurrentPassword().equals(userService.getUser(siteUser.getUsername()).getPassword())){
            bindingResult.rejectValue("currentPassword","passwordIncorrect","현재 비밀번호가 일치하지 않습니다.");
        }
        if(!userPasswordUpdateForm.getNewPassword().equals(userPasswordUpdateForm.getNewPassword2())){
            bindingResult.rejectValue("password2","passwordIncorrect","비밀번호 확인이 일치하지 않습니다.");
            return "passwordUpdate_form";
        }
        try{
            userService.passwordUpdate(siteUser.getUsername(), userPasswordUpdateForm.getNewPassword());
        }catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("updateFailed",e.getMessage());
            return "passwordUpdate_form";
        }
        return "redirect:/";   
    }
}
