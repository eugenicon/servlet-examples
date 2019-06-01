package net.example.tranforemer;

import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

@Component
public class GenericTransformer implements Transformer<Object> {

    @Override
    public Object transform(HttpServletRequest r, Parameter p) {
        return r.getAttribute(p.getName()) == null ? r.getParameter(p.getName()) : r.getAttribute(p.getName());
    }

    @Override
    public Class<Object> getSupportedType() {
        return Object.class;
    }
}
