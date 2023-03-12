package donga.merchant.entity;

import donga.merchant.entity.item.Item;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private Category category;

    //==연관관계 메서드==//
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.setPost(this);
    }

    //--생성 메서드--//
    public static Post createPost(String title, String content, Item... items) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        for (Item item : items) {
            post.addItem(item);
        }

        return post;
    }


}
