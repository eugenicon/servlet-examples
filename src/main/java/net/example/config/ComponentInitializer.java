package net.example.config;

import net.example.controller.GroupController;
import net.example.controller.UserController;
import net.example.controller.WelcomeController;
import net.example.data.dao.DataSource;
import net.example.data.dao.GroupDao;
import net.example.data.dao.UserDao;
import net.example.service.GroupService;
import net.example.service.UserService;
import net.example.servlet.RequestResolver;
import net.example.tranforemer.UserTransformer;

public class ComponentInitializer {
    private static ComponentInitializer instance;

    private final WelcomeController welcomeController;
    private final UserController userController;
    private final GroupController groupController;
    private final RequestResolver requestResolver;

    public ComponentInitializer() {
        DataSource dataSource = new DataSource();

        GroupDao groupDao = new GroupDao(dataSource);
        UserDao userDao = new UserDao(dataSource, groupDao);

        UserService userService = new UserService(userDao);
        GroupService groupService = new GroupService(groupDao);

        welcomeController = new WelcomeController();
        userController = new UserController(userService, groupService);
        groupController = new GroupController(groupService);

        UserTransformer userTransformer = new UserTransformer(groupService);

        requestResolver = new RequestResolver(welcomeController, userController, groupController, userTransformer);
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
