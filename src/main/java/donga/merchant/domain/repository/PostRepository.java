package donga.merchant.domain.repository;

import donga.merchant.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByMemberId(Pageable pageable, Long memberId);
}
