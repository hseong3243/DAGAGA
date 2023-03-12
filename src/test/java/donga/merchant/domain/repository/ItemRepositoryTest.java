package donga.merchant.domain.repository;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 아이템저장() {
        Member member = Member.createMember("123456", "1234!", "성");
//        Item item = Item.createItem("아이템1", 10000, "ON_SALE");
        Member findMember = memberRepository.save(member);
//        Item findItem = itemRepository.save(item);

        em.flush();
        em.clear();

        List<Item> result = itemRepository.findAll();

        assertThat(result.get(0).getName()).isEqualTo("아이템1");



    }

}