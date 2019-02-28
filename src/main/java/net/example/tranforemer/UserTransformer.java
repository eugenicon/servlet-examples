package net.example.tranforemer;

import net.example.data.User;

import javax.servlet.http.HttpServletRequest;

public class UserTransformer implements Transformer<User> {
    @Override
    public User transform(HttpServletRequest request) {
        User user = new User();
        user.setAge(Integer.parseInt(request.getParameter("age")));
        user.setName(request.getParameter("name"));
        return user;
    }
}
