package net.example.tranforemer;

import net.example.data.model.Group;
import net.example.data.model.User;
import net.example.service.GroupService;

import javax.servlet.http.HttpServletRequest;

public class UserTransformer implements Transformer<User> {
    private GroupService groupService;

    public UserTransformer(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public User transform(HttpServletRequest request) {
        User user = new User();
        String age = request.getParameter("age");
        if (age != null && age.matches("\\d+")) {
            user.setAge(Integer.parseInt(age));
        }
        user.setName(request.getParameter("name"));
        try {
            int groupId = Integer.parseInt(request.getParameter("group_id"));
            Group group = groupService.getById(groupId);
            user.setGroup(group);
        } catch (Exception e) {
            // do nothing
        }
        return user;
    }

    @Override
    public Class<User> getSupportedType() {
        return User.class;
    }
}
