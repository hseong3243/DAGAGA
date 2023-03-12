package donga.merchant.domain.entity.item;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private String itemCode; //물품 상태


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //==연관관계 메서드==//
    public void setPost(Post post) {
        this.post = post;
    }

//    public static Item createItem(String name, int price, String itemCode) {
//        Item item = new Item();
//        item.setName(name);
//        item.setPrice(price);
//        item.setItemCode(itemCode);
//
//        return item;
//    }
}
