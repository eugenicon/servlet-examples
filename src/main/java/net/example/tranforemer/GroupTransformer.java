package net.example.tranforemer;

import net.example.data.model.Group;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

public class GroupTransformer implements Transformer<Group> {

    @Override
    public Group transform(HttpServletRequest request, Parameter parameter) {
        Group group = new Group();
        group.setName(request.getParameter("name"));
        return group;
    }

    @Override
    public Class<Group> getSupportedType() {
        return Group.class;
    }
}
