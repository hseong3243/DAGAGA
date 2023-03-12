package donga.merchant.domain.repository;

import donga.merchant.web.controller.item.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<ItemDto> searchV1(String condition, String status, Pageable pageable);

    Page<ItemDto> searchDetail(String condition, String author, String status, Pageable pageable);
}
