package net.example.service;

import net.example.data.dto.UserLoginDto;
import net.example.data.model.Role;
import net.example.data.model.User;
import net.example.resolver.Component;
import net.example.tranforemer.TransformationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthenticationService {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);
    private static final String ALL_AUTH = "usersAuth";
    private static final String AUTH = "auth";

    private final TransformationService transformationService;
    private final UserService userService;

    public AuthenticationService(TransformationService transformationService, UserService userService) {
        this.transformationService = transformationService;
        this.userService = userService;
    }

    public void init(ServletContext servletContext) {
        servletContext.setAttribute(ALL_AUTH, new HashMap<String, AuthenticatedUser>());
    }

    public Optional<AuthenticatedUser> getAuthentication(HttpSession session) {
        return Optional.ofNullable(getAuthentications(session.getServletContext()).get(session.getId()));
    }

    @SuppressWarnings("unchecked")
    private Map<String, AuthenticatedUser> getAuthentications(ServletContext servletContext) {
        return (Map<String, AuthenticatedUser>) servletContext.getAttribute(ALL_AUTH);
    }

    public String getUserId(HttpServletRequest httpRequest) {
        return Optional.ofNullable(httpRequest.getParameter("userName"))
                .orElseGet(() -> getAuthentication(httpRequest.getSession())
                        .map(AuthenticatedUser::getName).orElse(""));
    }

    public boolean isUserHasOtherOpenSessions(String userId, HttpSession session) {
        List<String> userSessions = getAuthenticatedUserSessions(userId, session.getServletContext());
        return !userSessions.isEmpty() && (userSessions.size() > 1 || !userSessions.contains(session.getId()));
    }

    public void finishUserSessions(String key, HttpSession session) {
        ServletContext context = session.getServletContext();
        getAuthenticatedUserSessions(key, context).forEach(sessionId -> removeSession(sessionId, context));
        resetAuthentication(session);
    }

    public void resetAuthentication(HttpSession session) {
        session.setAttribute(AUTH, new AuthenticatedUser("UNKNOWN", Role.UNKNOWN));
    }

    private void removeSession(String sessionId, ServletContext context) {
        getAuthentications(context).remove(sessionId);
        LOGGER.debug("removed session {}",  sessionId);
    }

    private List<String> getAuthenticatedUserSessions(String key, ServletContext context) {
        return getAuthentications(context).entrySet().stream()
                .filter(e -> e.getValue().getName().equals(key) || e.getKey().equals(key))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public boolean isUserSessionStarted(String userId, HttpSession session) {
        return getAuthentication(session).filter(a -> a.getName().equals(userId)).isPresent();
    }

    public void startUserSession(HttpServletRequest httpRequest) {
        UserLoginDto userDto = transformationService.transform(httpRequest, UserLoginDto.class);
        AuthenticatedUser auth = new AuthenticatedUser(userDto.getUserName(), Role.UNKNOWN);
        getAuthentications(httpRequest.getServletContext()).put(httpRequest.getSession().getId(), auth);
        httpRequest.getSession().setAttribute(AUTH, auth);
    }

    public void login(HttpSession session, String userName, String password) throws ServiceException {
        User user = userService.findMatchingCredentials(userName, password).orElseThrow(() -> new ServiceException("Invalid username or password"));
        AuthenticatedUser auth = getAuthentication(session).orElseThrow(() -> new ServiceException("Authentication error"));
        auth.setName(user.getName());
        auth.setRole(user.getRole());
        LOGGER.debug("login user {}, session: {}, role: {}",  auth.getName(), session.getId(), auth.getRole());
    }

    public void logout(HttpSession session) {
        getAuthentication(session).ifPresent(auth -> finishUserSessions(auth.getName(), session));
        LOGGER.debug("logout for session session: {}", session.getId());
    }
}