package net.example.data.dto;

import net.example.data.validation.UserRegistrationValidator;
import net.example.data.validation.Valid;
import net.example.data.validation.ValidRegex;

@Valid(UserRegistrationValidator.class)
public class UserRegistrationDto {
    private String userName;
    private String password;
    private String passwordConfirmation;

    @ValidRegex(regex = ".{4,}", message = "valid-name-length")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ValidRegex(regex = ".{5,}", message = "valid-name-length")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
