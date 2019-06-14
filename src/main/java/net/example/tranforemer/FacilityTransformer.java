package net.example.tranforemer;

import net.example.data.model.Facility;
import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class FacilityTransformer implements Transformer<Facility> {
    private IntegerTransformer integerTransformer;

    public FacilityTransformer(IntegerTransformer integerTransformer) {
        this.integerTransformer = integerTransformer;
    }

    @Override
    public Facility transform(HttpServletRequest request, String parameter) {
        Facility entity = new Facility();
        entity.setId(integerTransformer.transform(request, "id"));
        entity.setName(request.getParameter("name"));
        return entity;
    }

    @Override
    public Class<Facility> getSupportedType() {
        return Facility.class;
    }
}
