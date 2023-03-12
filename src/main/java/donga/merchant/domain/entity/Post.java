package donga.merchant.domain.entity;

import donga.merchant.domain.entity.item.Item;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;
    private LocalDate createdDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<UploadFile> imageFiles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

//    private Category category;

    //==연관관계 메서드==//
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setCreatedDate(LocalDate date) {
        this.createdDate = LocalDate.now();
    }

    public void setImageFiles(List<UploadFile> imageFiles) {
        this.imageFiles = imageFiles;
        for (UploadFile imageFile : imageFiles) {
            imageFile.setPost(this);
        }
    }

    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.setPost(this);
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }


    //--생성 메서드--//
    public static Post createPost(String title, String content, LocalDate date, Member member) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedDate(date);
        post.setMember(member);

        return post;
    }


}
