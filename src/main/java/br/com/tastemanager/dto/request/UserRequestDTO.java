package br.com.tastemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {

    @JsonProperty("name")
    @NotBlank(message = "You must provide a name.")
    private String name;

    @JsonProperty("email")
    @NotBlank(message = "E-mail is mandatory.")
    @Email(message = "The email must be valid.")
    private String email;

    @JsonProperty("login")
    @NotBlank(message = "Login is mandatory.")
    private String login;

    @JsonProperty("password")
    @NotBlank(message = "Password is mandatory.")
    private String password;

    @JsonProperty("typePerson")
    private String typePerson;

    @JsonProperty("address")
    private String address;

    @JsonAnySetter
    public void handleUnknownField(String key, Object value) {
        throw new IllegalArgumentException("This field doesn't exist: " + key);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login != null && login.contains(" ")) {
            throw new IllegalArgumentException("The 'login' cannot contain spaces.");
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypePerson() {
        return typePerson;
    }

    public void setTypePerson(String typePerson) {
        this.typePerson = typePerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
