package net.example.data.validation;

import net.example.data.dto.UserRegistrationDto;
import net.example.resolver.Component;

@Component
public class UserRegistrationValidator implements Validator<UserRegistrationDto> {
    @Override
    public boolean isValid(UserRegistrationDto data) {
        return data.getPassword().equals(data.getPasswordConfirmation());
    }

    @Override
    public String getMessage() {
        return "valid-password-match";
    }
}
