package donga.merchant.domain.repository;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class PostRepositoryTest {

    @Autowired PostRepository postRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void 단건저장() {
        Member member = Member.createMember("12345", "1234!", "테스터");
        Post post = Post.createPost("타이틀", "내용", LocalDate.now(), member);
        postRepository.save(post);

        em.flush();
        em.clear();

        Optional<Post> findPost = postRepository.findById(1L);

        assertThat(findPost.get().getContent()).isEqualTo("내용");
    }

    @Test
    public void 여러건저장() {
        Member member = Member.createMember("12345", "1234!", "테스터");
        Post post1 = Post.createPost("타이틀1", "내용1", LocalDate.now(), member);
        Post post2 = Post.createPost("타이틀2", "내용2", LocalDate.now(), member);
        Post post3 = Post.createPost("타이틀3", "내용3", LocalDate.now(), member);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        postRepository.flush();

        List<Post> result = postRepository.findAll();

        assertThat(result.get(0).getTitle()).isEqualTo("타이틀1");
    }


}