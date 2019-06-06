package net.example.tranforemer;

import net.example.data.dto.UserRegistrationDto;
import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserRegistrationDtoTransformer implements Transformer<UserRegistrationDto> {

    @Override
    public UserRegistrationDto transform(HttpServletRequest request, String parameter) {
        UserRegistrationDto user = new UserRegistrationDto();
        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        user.setPasswordConfirmation(request.getParameter("passwordConfirmation"));
        return user;
    }

    @Override
    public Class<UserRegistrationDto> getSupportedType() {
        return UserRegistrationDto.class;
    }
}
