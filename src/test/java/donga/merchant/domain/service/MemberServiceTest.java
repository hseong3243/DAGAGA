package donga.merchant.domain.service;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.repository.MemberRepository;
import donga.merchant.web.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;

    @Test
    public void 회원가입() {

        Member member = Member.createMember("kim", "asdf!123", "nick");
        Long memberId = memberService.addMember(member);

        Optional<Member> result = memberRepository.findById(memberId);

        assertThat(result.get()).isEqualTo(member);
    }

}