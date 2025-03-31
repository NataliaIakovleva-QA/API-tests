package ru.iakovleva.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
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

    public User() {}
    public User(Integer id, String username, String firstName, String lastName, String email, String password, String phone, Integer userStatus) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "{ \"id\": \"" + id + "\", \"username\": \"" + username + "\", \"firstName\": \"" + firstName + "\", " +
                "\"lastName\": \"" + lastName + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\"," +
                " \"phone\": \"" + phone + "\", \"userStatus\": \"" + userStatus + "\" }";
    }
}

