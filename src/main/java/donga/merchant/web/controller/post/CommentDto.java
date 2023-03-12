package donga.merchant.web.controller.post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private Long postId;
    private String postTitle;


    public CommentDto(Long id, String content, Long postId, String postTitle) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.postTitle = postTitle;
    }
}
