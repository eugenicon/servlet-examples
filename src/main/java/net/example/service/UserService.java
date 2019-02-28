package net.example.service;

import net.example.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserService {
    private static final Random RANDOM = new Random();
    private List<User> userDataSource = new ArrayList<>();

    public UserService() {
        this.userDataSource.addAll(IntStream.range(0, 5)
                .mapToObj(i -> new User("name" + i, RANDOM.nextInt(70)))
                .collect(Collectors.toList()));
    }

    public List<User> getAllUsers() {
        return userDataSource;
    }

    public boolean addUser(User user) throws ServiceException {
        if (user != null) {
            boolean isDuplicate = userDataSource.stream().anyMatch(u -> u.getName().equals(user.getName()));
            if (!isDuplicate) {
                return userDataSource.add(user);
            }
        }
        throw new ServiceException("Could not save user");
    }
}
