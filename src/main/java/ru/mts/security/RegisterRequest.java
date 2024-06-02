package ru.mts.security;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
