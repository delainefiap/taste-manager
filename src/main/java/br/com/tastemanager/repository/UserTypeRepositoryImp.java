package br.com.tastemanager.repository;

import br.com.tastemanager.entity.UserType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserTypeRepositoryImp implements UserTypeRepository {

    private final JdbcClient jdbcClient;

    public UserTypeRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Integer save(UserType userType) {
        return this.jdbcClient
                .sql("INSERT INTO user_types (name) VALUES (:name)")
                .param("name", userType.getName())
                .update();
    }

    @Override
    public Integer updateUserType(Long id, UserType userType) {
        return this.jdbcClient
                .sql("UPDATE user_types SET name = COALESCE(:name, name) WHERE id = :id")
                .param("name", userType.getName())
                .param("id", id)
                .update();
    }

    @Override
    public Integer deleteUserType(Long id) {
        return this.jdbcClient
                .sql("DELETE FROM user_types WHERE id = :id")
                .param("id", id)
                .update();
    }

    @Override
    public Optional<UserType> findById(Long id) {
        return this.jdbcClient
                .sql("SELECT * FROM user_types WHERE id = :id")
                .param("id", id)
                .query(UserType.class)
                .optional();
    }

    @Override
    public Optional<UserType> findByName(String name) {
        return this.jdbcClient
                .sql("SELECT * FROM user_types WHERE name = :name")
                .param("name", name)
                .query(UserType.class)
                .optional();
    }

    @Override
    public List<UserType> findAll(int size, int offset) {
        return this.jdbcClient
                .sql("SELECT * FROM user_types LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(UserType.class)
                .list();
    }
}