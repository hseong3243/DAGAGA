package donga.merchant.entity;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void test1() {
//        Member member1 = new Member("kim");
//        memberRepository.save(member1);

    }


}