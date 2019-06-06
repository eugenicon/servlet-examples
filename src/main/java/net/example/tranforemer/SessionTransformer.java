package net.example.tranforemer;

import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SessionTransformer implements Transformer<HttpSession> {

    @Override
    public HttpSession transform(HttpServletRequest r, String p) {
        return r.getSession();
    }

    @Override
    public Class<HttpSession> getSupportedType() {
        return HttpSession.class;
    }
}
