package net.example.config;

import net.example.controller.*;
import net.example.data.dao.DataSource;
import net.example.data.dao.GroupDao;
import net.example.data.dao.UserDao;
import net.example.data.validation.NotEmpty;
import net.example.data.validation.ValidNumber;
import net.example.data.validation.ValidRegex;
import net.example.data.validation.ValidationService;
import net.example.service.GroupService;
import net.example.service.UserService;
import net.example.servlet.RequestResolver;
import net.example.tranforemer.GroupTransformer;
import net.example.tranforemer.TransformationService;
import net.example.tranforemer.UserTransformer;
import net.example.util.ResourceReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.Properties;

public class ComponentInitializer {
    private static final Logger LOGGER = LogManager.getLogger(ComponentInitializer.class);
    private static ComponentInitializer instance;

    private final RequestResolver requestResolver;

    private ComponentInitializer() {
        LOGGER.debug("ComponentInitializer created");
        Properties properties = ResourceReader.getResourceAsProperties("application.properties");

        DataSource dataSource = new DataSource(properties);

        GroupDao groupDao = new GroupDao(dataSource);
        UserDao userDao = new UserDao(dataSource, groupDao);

        UserService userService = new UserService(userDao);
        GroupService groupService = new GroupService(groupDao);

        ValidationService validationService = new ValidationService(
                new ValidRegex.AnnotationProcessor(),
                new NotEmpty.AnnotationProcessor(),
                new ValidNumber.AnnotationProcessor()
        );

        UserTransformer userTransformer = new UserTransformer(groupService);
        GroupTransformer groupTransformer = new GroupTransformer();
        TransformationService transformationService = new TransformationService(userTransformer, groupTransformer);
        transformationService.register(Object.class, (r, p) -> r.getAttribute(p.getName()) == null ? r.getParameter(p.getName()) : r.getAttribute(p.getName()));
        transformationService.register(HttpSession.class, (r, p) -> r.getSession());

        WelcomeController welcomeController = new WelcomeController();
        UserController userController = new UserController(userService, groupService);
        GroupController groupController = new GroupController(groupService);
        ErrorController errorController = new ErrorController();
        LocalizationController localizationController = new LocalizationController();

        requestResolver = new RequestResolver(transformationService,validationService,
                welcomeController, userController, groupController, errorController, localizationController);
    }

    public static ComponentInitializer getInstance() {
        if (instance == null) {
            synchronized (ComponentInitializer.class) {
                if (instance == null) {
                    instance = new ComponentInitializer();
                }
            }
        }

        return instance;
    }

    public RequestResolver getRequestResolver() {
        return requestResolver;
    }
}
