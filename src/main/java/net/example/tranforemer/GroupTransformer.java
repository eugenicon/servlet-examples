package net.example.tranforemer;

import net.example.data.model.Group;

import javax.servlet.http.HttpServletRequest;

public class GroupTransformer implements Transformer<Group> {

    @Override
    public Group transform(HttpServletRequest request) {
        Group group = new Group();
        group.setName(request.getParameter("name"));
        return group;
    }

    @Override
    public Class<Group> getSupportedType() {
        return Group.class;
    }
}
