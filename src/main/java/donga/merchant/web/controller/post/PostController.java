package donga.merchant.web.controller.post;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.item.ItemCode;
import donga.merchant.web.argumentresolver.Login;
import donga.merchant.web.file.FileStore;
import donga.merchant.web.service.CommentService;
import donga.merchant.web.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final FileStore fileStore;

    @ModelAttribute("itemCodes")
    public List<ItemCode> itemStatuses() {
        List<ItemCode> itemCodes = new ArrayList<>();
        itemCodes.add(new ItemCode("ON_SALE", "판매중"));
        itemCodes.add(new ItemCode("RESERVED", "거래중"));
        itemCodes.add(new ItemCode("OFF_SALE", "판매 완료"));
        return itemCodes;
    }

    @GetMapping
    public String postList(Model model, @Login Member loginMember,
                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<PostDto> postDtos = postService.findAll(pageable);
        model.addAttribute("posts", postDtos);

        if (loginMember == null) {
            return "postList";
        }

        model.addAttribute("member", loginMember);

        return "postList";
    }

    @GetMapping("/write")
    public String writeForm(Model model, @Login Member loginMember) {

        model.addAttribute("member", loginMember);

        return "write";
    }

    @PostMapping("/write")
    public String writePost(@ModelAttribute("post") PostDto postDto,
                            @Login Member loginMember) throws IOException {
        Long postId = postService.writePost(postDto, loginMember);

//        itemService.save(postDto.getItems(), postId, loginMember);

        return "redirect:/posts/" + postId;
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model, @Login Member loginMember) {

        PostDto postDto = postService.findPost(postId);
        model.addAttribute("post", postDto);


        if (loginMember == null) {
            return "seewriting";
        }

        model.addAttribute("member", loginMember);

        return "seewriting";
    }

    @PostMapping("/{postId}")
    public String postComment(@PathVariable Long postId, @Login Member loginMember,
                              @RequestParam String comment, @RequestParam("interested") Long interestedItem) {
        log.info("comment={}", comment);
        log.info("id={}", interestedItem);
        commentService.writeComment(postId, loginMember, comment, interestedItem);

        return "redirect:/posts/" + postId;
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model, @Login Member loginMember) {

        PostDto postDto = postService.findPost(postId);
        log.info("status={}", postDto.getItems().get(0).getItemCode());
        model.addAttribute("post", postDto);
        model.addAttribute("member", loginMember);

        return "edit";
    }

    @PostMapping("/{postId}/edit")
    public String postEdit(@PathVariable Long postId, @Login Member loginMember, @ModelAttribute("post") PostDto postDto) {
        postDto.setId(postId);

        postService.editPost(postDto);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{postId}/delete")
    public String postDelete(@PathVariable Long postId) {
        postService.delete(postId);
        log.info("postId={}", postId);

        return "redirect:/posts";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        log.info("이미지 조회");
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
}
