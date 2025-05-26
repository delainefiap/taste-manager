package br.com.tastemanager.repository;

import br.com.tastemanager.entity.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final JdbcClient jdbcClient;

    public UserRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Integer createUser(User user) {
        return this.jdbcClient
                .sql("INSERT INTO users (name, email, login, password, create_at, type_person, address) VALUES (:name, :email, :login, :password, :createAt, :typePerson, :address)")
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("createAt", LocalDateTime.now())
                .param("typePerson", user.getTypePerson())
                .param("address", user.getAddress())
                .update();
    }

    @Override
    public Integer updateUser(User user, Long id) {
        return this.jdbcClient
                .sql("UPDATE users SET name = :name, email = :email, login = :login, password = :password, lastUpdate = :lastUpdate, type_person = :typePerson, address = :address WHERE id = :id")
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("lastUpdate", LocalDateTime.now())
                .param("typePerson", user.getTypePerson())
                .param("address", user.getAddress())
                .param("id", id)
                .update();
    }

    @Override
    public Integer updatePassword( Long id, String password) {
        return this.jdbcClient
                .sql("UPDATE users SET password = :password, last_Update = :lastUpdate WHERE id = :id")
                .param("password", password)
                .param("id", id)
                .param("lastUpdate", LocalDateTime.now())
                .update();
    }

    @Override
    public Integer deleteUser(Long id) {
        return this.jdbcClient
                .sql("DELETE FROM users WHERE id = :id")
                .param("id", id)
                .update();
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.jdbcClient
                .sql("SELECT * FROM users WHERE id = :id")
                .param("id", id)
                .query(User.class)
                .optional();
    }

    @Override
    public List<User> findAll(int size, int offset) {
        return this.jdbcClient
                .sql("SELECT * FROM users LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(User.class)
                .list();
    }

}
