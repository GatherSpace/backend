package com.hari.gatherspace.dto;

import com.hari.gatherspace.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class UserSigninDTO {

    private String username;
    private String password;






    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
