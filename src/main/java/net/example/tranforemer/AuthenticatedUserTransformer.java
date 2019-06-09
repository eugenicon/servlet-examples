package net.example.tranforemer;

import net.example.resolver.Component;
import net.example.resolver.Lazy;
import net.example.service.AuthenticatedUser;
import net.example.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticatedUserTransformer implements Transformer<AuthenticatedUser> {
    private final Lazy<AuthenticationService> authenticationService;

    public AuthenticatedUserTransformer(Lazy<AuthenticationService> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public AuthenticatedUser transform(HttpServletRequest r, String p) {
        return authenticationService.get().getAuthentication(r.getSession()).orElse(null);
    }

    @Override
    public Class<AuthenticatedUser> getSupportedType() {
        return AuthenticatedUser.class;
    }
}
