package br.com.tastemanager.dto.request;

import br.com.tastemanager.entity.UserType;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserTypeRequestDTO {

    @JsonProperty("name")
    @NotBlank(message = "You must provide a name.")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

}
