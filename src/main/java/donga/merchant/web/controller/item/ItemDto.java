package donga.merchant.web.controller.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ItemDto {

    private Long id;
    private String name;
    private Integer price;
    private String itemCode;
    private String author;
    private Long postId;
    private Long memberId;

    public ItemDto() {
    }

    public ItemDto(String name, Integer price, String author, String itemCode) {
        this.name = name;
        this.price = price;
        this.author = author;
        this.itemCode = itemCode;
    }

    public ItemDto(Long id, String name, Integer price, String author, String itemCode) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.itemCode = itemCode;
    }

    @QueryProjection
    public ItemDto(Long id, String name, Integer price, String author, String itemCode, Long postId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.itemCode = itemCode;
        this.postId = postId;
    }
}
