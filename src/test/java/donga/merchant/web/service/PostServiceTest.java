package donga.merchant.web.service;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.Post;
import donga.merchant.domain.repository.MemberRepository;
import donga.merchant.domain.repository.PostRepository;
import donga.merchant.web.controller.post.PostDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 게시글_저장() throws IOException {
        Member member = Member.createMember("12345", "1234!", "테스터");
        memberRepository.save(member);

        em.flush();
        em.clear();

        PostDto postDto = new PostDto("title", "content");
        Long postId = postService.writePost(postDto, member);

        em.flush();
    }

    @Test
    public void 게시글_불러오기() throws IOException {
        Member member = Member.createMember("12345", "1234!", "테스터");
        memberRepository.save(member);

        em.flush();
        em.clear();

        PostDto postDto = new PostDto("title", "content");
        Long postId = postService.writePost(postDto, member);

        em.flush();

        Optional<Post> findPost = postRepository.findById(postId);

        findPost.ifPresent(post -> {
            assertThat(post.getId()).isEqualTo(postId);
        });
    }

}