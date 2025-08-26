package com.clinic.main.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class SignupRequestDto {

    @Email
    private String username;

    @NotBlank
    @Length(min = 8, max = 30)
    private String password;

    @NotBlank
    private String phoneNo;

    public SignupRequestDto() {
    }

    public SignupRequestDto(String username, String password, String phoneNo) {
        this.username = username;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
