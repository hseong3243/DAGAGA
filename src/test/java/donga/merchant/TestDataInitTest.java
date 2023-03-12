package donga.merchant;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.repository.ItemRepository;
import donga.merchant.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TestDataInitTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void test1() {
        Member member = Member.createMember("123456", "1234!", "테스터");
        memberRepository.save(member);
    }

}