package br.com.tastemanager.repository;

import br.com.tastemanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Integer save(User user);

    Integer updateUser(User user);

    Integer updatePassword( Long id, String password);

    Integer deleteUser(String login);

    Optional<User> findById(Long id);

    Optional<Long> findIdByLogin(String login);

    List<User> findAll(int size, int offset);




}
