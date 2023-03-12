package donga.merchant.web.service;

import donga.merchant.domain.entity.Comment;
import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.Post;
import donga.merchant.domain.repository.CommentRepository;
import donga.merchant.domain.repository.MemberRepository;
import donga.merchant.domain.repository.PostRepository;
import donga.merchant.web.controller.post.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void writeComment(Long postId, Member member, String comment, Long interestedItem) {

        Comment newComment = Comment.write(comment, interestedItem);

        Optional<Post> findPost = postRepository.findById(postId);
        Post post = findPost.get();
        log.info("member={}", member);
        log.info("memberId={}", member.getId());
        Optional<Member> loginMember = memberRepository.findById(member.getId());
        Member findMember = loginMember.get();
        newComment.setMember(findMember);
        newComment.setPost(post);

        commentRepository.save(newComment);
    }

    public Page<CommentDto> findAll(Pageable pageable) {
        Page<Comment> result = commentRepository.findAll(pageable);

        return result.map(c -> new CommentDto(c.getId(), c.getContent(), c.getPost().getId(), c.getPost().getTitle()));
    }

    public Page<CommentDto> findByMember(Pageable pageable, Long memberId) {
        Page<Comment> result = commentRepository.findByMemberId(memberId, pageable);

        return result.map(c -> new CommentDto(c.getId(), c.getContent(), c.getPost().getId(), c.getPost().getTitle()));
    }
}
