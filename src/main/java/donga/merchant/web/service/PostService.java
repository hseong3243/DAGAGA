package donga.merchant.web.service;

import donga.merchant.domain.entity.UploadFile;
import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.Post;
import donga.merchant.domain.entity.item.Book;
import donga.merchant.domain.entity.item.Item;
import donga.merchant.domain.repository.ItemRepository;
import donga.merchant.domain.repository.MemberRepository;
import donga.merchant.domain.repository.PostRepository;
import donga.merchant.web.controller.item.ItemDto;
import donga.merchant.web.controller.post.PostDto;
import donga.merchant.web.file.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final FileStore fileStore;

    public List<Post> posts() {
        return postRepository.findAll();
    }

    public Page<Post> postList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional
    public Long writePost(PostDto postDto, Member member) throws IOException {
        //멤버 조회
        Optional<Member> findMember = memberRepository.findById(member.getId());

        //파일 저장
        List<UploadFile> storeImageFiles = fileStore.storeFiles(postDto.getImageFiles());

        //게시글 생성
        Post post = Post.createPost(postDto.getTitle(), postDto.getContent(), LocalDate.now(), findMember.get());
        post.setImageFiles(storeImageFiles);
        postRepository.save(post);


        itemService.save(postDto.getItems(), post.getId(), member);

        return post.getId();
    }

    //    public Page<PostDto> findAll(PageRequest pageRequest) {
//        Page<Post> posts = postRepository.findAll(pageRequest);
//
//        return posts.map(p -> new PostDto(p.getId(), p.getTitle(), p.getCreatedDate(), p.getMember().getNickName()));
//    }
    public Page<PostDto> findAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(p -> new PostDto(p.getId(), p.getTitle(), p.getCreatedDate(), p.getMember().getNickName()));
    }

    public Page<PostDto> findByMember(Pageable pageable, Long memberId) {
        Page<Post> result = postRepository.findByMemberId(pageable, memberId);

        return result.map(p -> new PostDto(p.getId(), p.getTitle(), p.getCreatedDate(), p.getMember().getNickName()));
    }

    public PostDto findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 데이터입니다."));

        Member member = memberRepository.findById(post.getMember().getId())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

//        List<ItemDto> itemDtos = post.getItems().stream()
//                .map(i -> new ItemDto(i.getName(), i.getPrice(), i.getAuthor(), i.getItemCode()))
//                .collect(Collectors.toList());

        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : post.getItems()) {
            Book book = (Book) item;
            itemDtos.add(new ItemDto(book.getId(), book.getName(), book.getPrice(), book.getAuthor(), book.getItemCode()));
        }

        return new PostDto(post.getId(), post.getTitle(), post.getContent(), itemDtos, post.getComments(),
                post.getMember().getId(), member.getNickName(), post.getImageFiles());
    }

    @Transactional
    public void editPost(PostDto postDto) {
        Optional<Post> findPost = postRepository.findById(postDto.getId());

        findPost.ifPresent(p -> {
            int size = p.getItems().size();
            int newSize = postDto.getItems().size();
            List<ItemDto> itemDtos = postDto.getItems();

            List<Item> items = p.getItems();
            for (int i=0; i<items.size(); i++) {
                Book book = (Book) items.get(i);
                book.setName(itemDtos.get(i).getName());
                book.setPrice(itemDtos.get(i).getPrice());
                book.setAuthor(itemDtos.get(i).getAuthor());
                book.setItemCode(itemDtos.get(i).getItemCode());
            }

            for (int i=size; i<newSize; i++) {
                Item item = Book.createBook(itemDtos.get(i).getName(), itemDtos.get(i).getPrice(), itemDtos.get(i).getAuthor(), itemDtos.get(i).getItemCode());
                item.setPost(p);
                item.setMember(p.getMember());

                p.getItems().add(item);
            }
            p.updatePost(postDto.getTitle(), postDto.getContent());
        });
    }

    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }


}
