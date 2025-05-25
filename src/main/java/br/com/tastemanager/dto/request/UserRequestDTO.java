package br.com.tastemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("typePerson")
    private String typePerson;

    @JsonProperty("address")
    private String address;

}
