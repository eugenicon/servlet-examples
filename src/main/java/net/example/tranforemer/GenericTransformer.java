package net.example.tranforemer;

import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class GenericTransformer implements Transformer<Object> {

    @Override
    public Object transform(HttpServletRequest r, String p) {
        return r.getAttribute(p) == null ? r.getParameter(p) : r.getAttribute(p);
    }

    @Override
    public Class<Object> getSupportedType() {
        return Object.class;
    }
}
