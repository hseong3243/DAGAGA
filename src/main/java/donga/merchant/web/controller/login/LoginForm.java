package donga.merchant.web.controller.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotEmpty
    private String studentNumber;

    @NotEmpty
    private String password;
}
