package br.com.tastemanager.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequest {
    private String oldPassword;

    @NotBlank(message = "The new password cannot be empty or null.")
    private String newPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
