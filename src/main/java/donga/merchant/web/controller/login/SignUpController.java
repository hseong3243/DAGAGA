package donga.merchant.web.login;

import donga.merchant.domain.entity.Member;
import donga.merchant.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/login/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final MemberService memberService;

    @GetMapping
    public String signUpForm(@ModelAttribute("signUpForm") SignUpForm signUpForm) {
        return "signup";
    }

    @PostMapping
    public String addMember(@Valid @ModelAttribute SignUpForm signUpForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        Member newMember = Member.createMember(signUpForm.getStudentNumber(), signUpForm.getPassword(), signUpForm.getNickName());
        memberService.addMember(newMember);

        return "redirect:/login";
    }
}
