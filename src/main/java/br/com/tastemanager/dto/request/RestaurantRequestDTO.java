package br.com.tastemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RestaurantRequestDTO {

    @JsonProperty("name")
    @NotBlank(message = "You must provide a name.")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("typeKitchen")
    private String typeKitchen;

    @JsonProperty("openingHours")
    @NotBlank(message = "You must provide the opening hours.")
    private String openingHours;

    @JsonProperty("owner")
    private String owner;

    @JsonAnySetter
    public void handleUnknownField(String key, Object value) {
        throw new IllegalArgumentException("This field doesn't exist: " + key);
    }


}
