package br.com.tastemanager.dto.request;

import lombok.Getter;
import lombok.Setter;

public class ChangePasswordRequest {
    private Long id;
    private String oldPassword;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
