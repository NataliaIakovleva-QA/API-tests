package ru.iakovleva.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Integer userStatus;


    @Override
    public String toString() {
        return "{ \"id\": \"" + id + "\", \"username\": \"" + username + "\", \"firstName\": \"" + firstName + "\", " +
                "\"lastName\": \"" + lastName + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\"," +
                " \"phone\": \"" + phone + "\", \"userStatus\": \"" + userStatus + "\" }";
    }
}

