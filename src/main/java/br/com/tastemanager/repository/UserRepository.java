package br.com.tastemanager.repository;

import br.com.tastemanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Integer createUser(User user);

    Integer updateUser(User user, Long id);

    Integer updatePassword(String password, Long id);

    Integer deleteUser(Long id);

    Optional<User> findById(Long id);

    List<User> findAll(int size, int offset);




}
