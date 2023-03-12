package donga.merchant.web.service;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.Post;
import donga.merchant.domain.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    MemberService memberService;
    @Autowired
    PostRepository postRepository;

    @Test
    void write() {
        Member member = Member.createMember("1234", "1234!", "test");
        Long memberId = memberService.addMember(member);
        Post post = Post.createPost("타이틀", "내용", LocalDate.now(), member);
        Post savedPost = postRepository.save(post);
//        commentService.writeComment(savedPost.getId(), member, "안녕");
    }
}