package donga.merchant.web.controller.mypage;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.Post;
import donga.merchant.domain.entity.item.Item;
import donga.merchant.domain.repository.ItemRepository;
import donga.merchant.domain.repository.MemberRepository;
import donga.merchant.domain.repository.PostRepository;
import donga.merchant.web.controller.post.PostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DataJpaTest
class myPageControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void create() {
        Member member = Member.createMember("12345", "1234!", "닉");
        Member saveMember = memberRepository.save(member);

        Post post = Post.createPost("타이틀", "내용", LocalDate.now(), saveMember);
        postRepository.save(post);

        em.flush();
        em.clear();
    }

    @Test
    void 조회() {
        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<Post> result = postRepository.findAll(pageRequest);

        Page<PostDto> posts = result.map(p -> new PostDto(p.getId(), p.getTitle(), p.getContent(), p.getCreatedDate(), p.getMember().getNickName()));

        System.out.println(result.getTotalElements());

        for (PostDto post : posts) {
            System.out.println("post = " + post.getTitle());
            System.out.println("post = " + post.getContent());
        }
    }

    @Test
    void 아이템_포스트조회() {
        List<Post> result = postRepository.findAll();
        for (Post post : result) {
            Member member = post.getMember();
            System.out.println("member = " + member);
//            Optional<Member> findMember = memberRepository.findById(member.getId());
//            System.out.println("findMember.get() = " + findMember.get());
        }

    }

}