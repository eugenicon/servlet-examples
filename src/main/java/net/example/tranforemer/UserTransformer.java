package net.example.tranforemer;

import net.example.data.model.Group;
import net.example.data.model.User;
import net.example.resolver.Component;
import net.example.service.GroupService;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserTransformer implements Transformer<User> {
    private GroupService groupService;
    private IntegerTransformer integerTransformer;

    public UserTransformer(GroupService groupService, IntegerTransformer integerTransformer) {
        this.groupService = groupService;
        this.integerTransformer = integerTransformer;
    }

    @Override
    public User transform(HttpServletRequest request, String parameter) {
        User user = new User();
        user.setId(integerTransformer.transform(request, "id"));
        user.setAge(integerTransformer.transform(request, "age"));
        user.setName(request.getParameter("name"));
        Group group = groupService.getById(integerTransformer.transform(request, "group_id"));
        user.setGroup(group);
        return user;
    }

    @Override
    public Class<User> getSupportedType() {
        return User.class;
    }
}
