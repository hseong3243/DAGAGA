package donga.merchant.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "comment_id")
    private Long id;

    private String content;
    private Date createdDate;
    private Long interestedItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    //==정적 팩토리 메서드==//
    public static Comment write(String content, Long interestedItem) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setInterestedItem(interestedItem);

        return comment;
    }

}
