package donga.merchant.domain.entity;

import donga.merchant.domain.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    protected Member() {
    }

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long id;
    private String studentNumber;
    private String password;
    private String nickName;
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<> ();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {

    }

    //==정적 팩토리 메서드==//
    public static Member createMember(String memberIdent, String memberPassword, String nickName) {
        Member member = new Member();
        member.setStudentNumber(memberIdent);
        member.setPassword(memberPassword);
        member.setNickName(nickName);
        member.setCreatedDate(LocalDateTime.now());

        return member;
    }
}
