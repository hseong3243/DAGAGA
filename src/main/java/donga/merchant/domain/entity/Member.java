package donga.merchant.entity;

import donga.merchant.entity.item.Item;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Member {

    protected Member() {
    }

    public Member(String memberIdent) {
        this.memberIdent = memberIdent;
    }

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String memberIdent;
    private String memberPasswd;
    private Date createdDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<> ();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
