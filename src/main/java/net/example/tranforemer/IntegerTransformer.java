package net.example.tranforemer;

import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

@Component
public class IntegerTransformer implements Transformer<Integer> {
    private final GenericTransformer genericTransformer;

    public IntegerTransformer(GenericTransformer genericTransformer) {
        this.genericTransformer = genericTransformer;
    }

    @Override
    public Integer transform(HttpServletRequest r, Parameter p) {
        String s = String.valueOf(genericTransformer.transform(r, p));
        return s.matches("\\d+") ? Integer.parseInt(s) : null;
    }

    @Override
    public Class<Integer> getSupportedType() {
        return Integer.class;
    }
}
