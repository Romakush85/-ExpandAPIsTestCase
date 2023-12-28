package org.expandapis.testcase.services;

import org.expandapis.testcase.models.User;
import org.expandapis.testcase.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public void save(User user) {
        usersRepository.save(user);
    }

    public User findByUserName(String userName) {
        return usersRepository.findByUsername(userName).orElse(null);
    }

    public void deleteByUsername(String username) {
        User userToDelete = findByUserName(username);
        if(userToDelete != null) usersRepository.deleteById(userToDelete.getId());
    }
}
