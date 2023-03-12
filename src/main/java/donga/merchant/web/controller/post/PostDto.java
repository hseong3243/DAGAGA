package donga.merchant.web.controller.post;

import donga.merchant.domain.entity.UploadFile;
import donga.merchant.domain.entity.Comment;
import donga.merchant.web.controller.item.ItemDto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private List<ItemDto> items;
    private LocalDate createdDate;
    private List<Comment> comments;
    private List<MultipartFile> imageFiles;
    private List<UploadFile> images;
    private Long memberId;
    private String memberNickname;

    public PostDto() {
    }

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdDate = LocalDate.now();
    }

    public PostDto(Long id, String title, LocalDate createdDate, String memberNickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.memberNickname = memberNickname;
    }

    public PostDto(Long id, String title, String content, List<ItemDto> items, List<Comment> comments, Long memberId,
                   String memberNickname, List<UploadFile> images) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.items = items;
        this.comments = comments;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.images = images;
    }

    public PostDto(Long id, String title, String content, LocalDate createdDate, String memberNickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.memberNickname = memberNickname;
    }

}
