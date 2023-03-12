package donga.merchant.web.controller.login;

import donga.merchant.domain.entity.Member;
import donga.merchant.web.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        //valid 에서 에러가 있으면 에러 내용을 뷰로 날린다.
        if (bindingResult.hasErrors()) {
            return "login";
        }

        Member loginMember = loginService.login(form.getStudentNumber(), form.getPassword());

        //리포지터리에 해당 멤버가 없어 널을 반환한 경우, bindingResult 에 에러코드를 담아서 뷰로 날린다.
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        //세션이 있으면 세션을 반환하고, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        //세션이 있으면 세션을 반환하되, create 를 false 로 두어서 신규 세션의 생성을 막는다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
