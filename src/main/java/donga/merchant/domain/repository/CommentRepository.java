package donga.merchant.domain.repository;

import donga.merchant.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByMemberId(Long memberId, Pageable pageable);
}
