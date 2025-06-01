package br.com.tastemanager.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequest {
    private String login;
    private String oldPassword;

    @NotBlank(message = "The new password cannot be empty or null.")
    private String newPassword;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return newPassword;
    }

    public void setPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
