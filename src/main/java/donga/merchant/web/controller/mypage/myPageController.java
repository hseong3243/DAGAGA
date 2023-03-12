package donga.merchant.web.controller.mypage;

import donga.merchant.domain.entity.Member;
import donga.merchant.web.argumentresolver.Login;
import donga.merchant.web.controller.item.ItemDto;
import donga.merchant.web.controller.post.PostDto;
import donga.merchant.web.controller.post.CommentDto;
import donga.merchant.web.service.CommentService;
import donga.merchant.web.service.ItemService;
import donga.merchant.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my-page")
@RequiredArgsConstructor
public class myPageController {

    private final PostService postService;
    private final ItemService itemService;
    private final CommentService commentService;

    @GetMapping
    public String myPage(@Login Member loginMember, Model model,
                         @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        if(loginMember == null) {
            return "myPage/myPage";
        }

        Page<PostDto> postDtos = postService.findByMember(pageable, loginMember.getId());
        Page<ItemDto> itemDtos = itemService.findByMember(pageable, loginMember.getId());
        Page<CommentDto> commentDtos = commentService.findByMember(pageable, loginMember.getId());

        model.addAttribute("posts", postDtos);
        model.addAttribute("items", itemDtos);
        model.addAttribute("comments", commentDtos);

        model.addAttribute("member", loginMember);
        return "myPage/myPage";
    }

    @GetMapping("/my-posts")
    public String myPosts(@Login Member loginMember, Model model,
                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> result = postService.findByMember(pageable, loginMember.getId());
        model.addAttribute("posts", result);
        model.addAttribute("member", loginMember);

        return "myPage/myPosts";
    }

    @GetMapping("/my-items")
    public String myItems(@Login Member loginMember, Model model,
                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ItemDto> result = itemService.findByMember(pageable, loginMember.getId());
        model.addAttribute("items", result);
        model.addAttribute("member", loginMember);

        return "myPage/myItems";
    }

    @GetMapping("my-comments")
    public String myComments(@Login Member loginMember, Model model,
                             @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentDto> result = commentService.findByMember(pageable, loginMember.getId());
        model.addAttribute("comments", result);
        model.addAttribute("member", loginMember);

        return "myPage/myComments";
    }
}
