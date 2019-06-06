package net.example.tranforemer;

import net.example.data.model.Group;
import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class GroupTransformer implements Transformer<Group> {
    private IntegerTransformer integerTransformer;

    public GroupTransformer(IntegerTransformer integerTransformer) {
        this.integerTransformer = integerTransformer;
    }

    @Override
    public Group transform(HttpServletRequest request, String parameter) {
        Group group = new Group();
        group.setId(integerTransformer.transform(request, "id"));
        group.setName(request.getParameter("name"));
        return group;
    }

    @Override
    public Class<Group> getSupportedType() {
        return Group.class;
    }
}
