package br.com.tastemanager.repository;

import br.com.tastemanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Integer save(User user);

    Integer updateUser(Long id, User user);

    Integer updatePassword(Long id, String password);

    Integer deleteUser(Long id);

    Optional<User> findById(Long id);

    Optional<User> findUserByLogin(String login);

    Optional<Long> findIdByLogin(String login);

    List<User> findAll(int size, int offset);




}
