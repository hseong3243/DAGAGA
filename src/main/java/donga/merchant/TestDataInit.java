package donga.merchant;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.Post;
import donga.merchant.domain.entity.item.Item;
import donga.merchant.domain.repository.ItemRepository;
import donga.merchant.domain.repository.MemberRepository;
import donga.merchant.domain.repository.PostRepository;
import donga.merchant.web.controller.item.ItemDto;
import donga.merchant.web.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    /**
     * 테스트용 계정 추가
     * 테스트용 게시글 추가
     */
//    @PostConstruct
    @Transactional
    public void init() {
        Member member = Member.createMember("1234567", "1234!", "테스터");
        Member member2 = Member.createMember("1620546", "1234!", "테스터2");
        Member member3 = Member.createMember("210210", "1234!", "테스터3");

        Member savedMember = memberRepository.save(member);
        Member savedMember2 = memberRepository.save(member2);
        Member savedMember3 = memberRepository.save(member3);

        Post post1 = Post.createPost("타이틀1", "내용1", LocalDate.now(), savedMember);
        Post post2 = Post.createPost("타이틀2", "내용2", LocalDate.now(), savedMember);
        Post post3 = Post.createPost("저장", "내용이올시다", LocalDate.now(), savedMember2);


        Post savedPost1 = postRepository.save(post1);
        postRepository.save(post2);
        Post savedPost2 = postRepository.save(post3);

        List<ItemDto> itemDtos = new ArrayList<>();
        ItemDto itemDto = new ItemDto("타이틀", 10000, "테스터", "ON_SALE");
        itemDtos.add(itemDto);

        List<ItemDto> itemDtos2 = new ArrayList<>();
        for(int i=0; i<100; i++) {
            ItemDto item = new ItemDto("학" + i, 10000 + i * 1000, "김전"+i, "ON_SALE");
            itemDtos2.add(item);
        }

        itemService.save(itemDtos, savedPost1.getId(), member);
        itemService.save(itemDtos2, savedPost2.getId(), member2);


    }
}
