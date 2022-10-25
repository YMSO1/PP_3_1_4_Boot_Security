package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> showAllUsers() {
        return repository.findAll();
    }

    @Override
    public User show(int id) {
        Optional<User> foundUser = repository.findById(id);
        return foundUser.orElse(null);
    }

    @Override
    public User show(String email) {
        Optional<User> foundUser = repository.findByName(email);
        return foundUser.orElse(null);
    }

    @Override
    @Transactional
    public void save(User user) {
        repository.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, User updateUser) {
        updateUser.setId(id);
        repository.save(updateUser);
    }
}
