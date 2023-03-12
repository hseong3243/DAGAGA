package donga.merchant.domain.entity.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Book extends Item{
    private String author;  //저자
    private String publisher; //출판사

    //==정적 팩토리 메서드==//
    public static Book createBook(String name, int price, String author, String itemCode) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setAuthor(author);
        book.setItemCode(itemCode);

        return book;
    }

}
