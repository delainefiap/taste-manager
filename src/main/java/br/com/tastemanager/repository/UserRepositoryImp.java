package br.com.tastemanager.repository;

import br.com.tastemanager.entity.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final JdbcClient jdbcClient;

    private final Clock clock;

    public UserRepositoryImp(JdbcClient jdbcClient, Clock clock) {
        this.jdbcClient = jdbcClient;
        this.clock = clock;
    }

    @Override
    public Integer save(User user) {
        return this.jdbcClient
                .sql("INSERT INTO users (name, email, login, password, created_at, type_person, address) VALUES (:name, :email, :login, :password, :createdAt, :typePerson, :address)")
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("createdAt", LocalDateTime.now(clock))
                .param("typePerson", user.getTypePerson())
                .param("address", user.getAddress())
                .update();
    }

    @Override
    public Integer updateUser(Long id, User user) {
        return this.jdbcClient
                .sql("UPDATE users SET " +
                        "name = COALESCE(:name, name), " +
                        "email = COALESCE(:email, email), " +
                        "last_update = :lastUpdate, " +
                        "type_person = COALESCE(:typePerson, type_person), " +
                        "address = COALESCE(:address, address) " +
                        "WHERE id = :id")
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("lastUpdate", LocalDateTime.now(clock))
                .param("typePerson", user.getTypePerson())
                .param("address", user.getAddress())
                .param("id", id)
                .update();
    }

    @Override
    public Integer updatePassword(Long id, String password) {
        return this.jdbcClient
                .sql("UPDATE users SET password = :password, last_update = :lastUpdate WHERE id = :id")
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
    public Optional<User> findUserByLogin(String login){
        return this.jdbcClient
                .sql("SELECT * FROM users WHERE login = :login")
                .param("login", login)
                .query(User.class)
                .optional();
    }

    @Override
    public Optional<Long> findIdByLogin(String login) {
        return this.jdbcClient
                .sql("SELECT id FROM users WHERE login = :login")
                .param("login", login)
                .query(Long.class)
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
