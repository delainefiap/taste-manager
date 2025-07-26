package br.com.tastemanager.repository;

import br.com.tastemanager.entity.UserType;

import java.util.List;
import java.util.Optional;

public interface UserTypeRepository {

    Integer save(UserType userType);

    Integer updateUserType(Long id, UserType userType);

    Integer deleteUserType(Long id);

    Optional<UserType> findById(Long id);

    Optional<UserType> findByName(String id);

    List<UserType> findAll(int size, int offset);
}