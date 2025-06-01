package br.com.tastemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateRequestDTO {

    @JsonProperty("name")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @JsonProperty("email")
    @NotBlank(message = "The email must be valid and cannot be blank.")
    @Email(message = "The email must be valid and cannot be blank.")
    private String email;

    @JsonProperty("typePerson")
    private String typePerson;

    @JsonProperty("address")
    private String address;

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


    public String getTypePerson() {
        return typePerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}