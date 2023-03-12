package donga.merchant.web.controller.login;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String studentNumber, String password) {
        Optional<Member> findMember = memberRepository.findByStudentNumber(studentNumber);

        //찾은 멤버의 패스워드와 패스워드 변수의 값이 같으면 멤버를 리턴, 아니면 널을 리턴
        return findMember.filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
