package net.example.config;

import net.example.controller.ErrorController;
import net.example.controller.GroupController;
import net.example.controller.UserController;
import net.example.controller.WelcomeController;
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
import net.example.tranforemer.UserTransformer;
import net.example.util.ResourceReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class ComponentInitializer {
    private static final Logger LOGGER = LogManager.getLogger(ComponentInitializer.class);
    private static ComponentInitializer instance;

    private final WelcomeController welcomeController;
    private final UserController userController;
    private final GroupController groupController;
    private final RequestResolver requestResolver;
    private final ErrorController errorController;

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

        welcomeController = new WelcomeController();
        userController = new UserController(userService, groupService, validationService);
        groupController = new GroupController(groupService);
        errorController = new ErrorController();

        UserTransformer userTransformer = new UserTransformer(groupService);

        requestResolver = new RequestResolver(welcomeController, userController, groupController, errorController, userTransformer);
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

    public WelcomeController getWelcomeController() {
        return welcomeController;
    }

    public UserController getUserController() {
        return userController;
    }

    public GroupController getGroupController() {
        return groupController;
    }

    public RequestResolver getRequestResolver() {
        return requestResolver;
    }
}
