package net.example.tranforemer;

import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class IntegerTransformer implements Transformer<Integer> {
    private final GenericTransformer genericTransformer;

    public IntegerTransformer(GenericTransformer genericTransformer) {
        this.genericTransformer = genericTransformer;
    }

    @Override
    public Integer transform(HttpServletRequest r, String p) {
        String s = String.valueOf(genericTransformer.transform(r, p));
        return s.matches("\\d+") ? Integer.parseInt(s) : 0;
    }

    @Override
    public Class<Integer> getSupportedType() {
        return Integer.class;
    }
}
