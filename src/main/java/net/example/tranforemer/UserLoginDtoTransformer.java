package net.example.tranforemer;

import net.example.data.dto.UserLoginDto;
import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

@Component
public class UserLoginDtoTransformer implements Transformer<UserLoginDto> {

    @Override
    public UserLoginDto transform(HttpServletRequest request, Parameter parameter) {
        UserLoginDto user = new UserLoginDto();
        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        return user;
    }

    @Override
    public Class<UserLoginDto> getSupportedType() {
        return UserLoginDto.class;
    }
}
