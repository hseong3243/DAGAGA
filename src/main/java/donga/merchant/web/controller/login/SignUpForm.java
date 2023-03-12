package donga.merchant.web.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignUpForm {

    @NotEmpty
    private String studentNumber;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickName;
}
