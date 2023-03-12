package donga.merchant.web.service;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.Post;
import donga.merchant.domain.entity.item.Book;
import donga.merchant.domain.entity.item.Item;
import donga.merchant.domain.repository.ItemRepository;
import donga.merchant.domain.repository.PostRepository;
import donga.merchant.web.controller.item.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final PostRepository postRepository;

    @Transactional
    public void save(List<ItemDto> itemDtoList, Long postId, Member member) {
        List<Item> items = new ArrayList<>();
        Optional<Post> findPost = postRepository.findById(postId);

        if (findPost.isPresent()) {
            for (ItemDto itemDto : itemDtoList) {
                Book item = Book.createBook(itemDto.getName(), itemDto.getPrice(), itemDto.getAuthor(), itemDto.getItemCode());
                item.setPost(findPost.get());
                item.setMember(member);
                items.add(item);

            }
        }

        itemRepository.saveAll(items);
    }

    public Page<ItemDto> findAll(Pageable pageRequest) {

        Page<Item> result = itemRepository.findAll(pageRequest);

        return result.map(this::convertToItemDto);
    }

    public Page<ItemDto> findByMember(Pageable pageable, Long memberId) {
        Page<Item> result = itemRepository.findByMemberId(pageable, memberId);

        return result.map(this::convertToItemDto);
    }

    private ItemDto convertToItemDto(Item i) {
        Book book = (Book) i;
        return new ItemDto(book.getId(), book.getName(), book.getPrice(), book.getAuthor(),
                book.getItemCode(), book.getPost().getId());
    }

    public Page<ItemDto> search(String condition, String status, Pageable pageable) {
        return itemRepository.searchV1(condition, status, pageable);
    }

    public Page<ItemDto> searchDetail(String query, String author, String status, Pageable pageable) {
        return itemRepository.searchDetail(query, author, status, pageable);
    }


}
