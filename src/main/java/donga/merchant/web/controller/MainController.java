package donga.merchant.web.controller;

import donga.merchant.domain.entity.Member;
import donga.merchant.web.argumentresolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String MainHome(@Login Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면 널
        if (loginMember == null) {
            return "main";
        }

        //세션이 유지되면 모델에 로그인한 유저의 정보를 담아서 홈으로 날려준다.
        model.addAttribute("member", loginMember);
        return "main";

    }

}
