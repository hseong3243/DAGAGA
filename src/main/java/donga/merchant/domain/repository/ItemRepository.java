package donga.merchant.domain.repository;

import donga.merchant.domain.entity.item.Item;
import donga.merchant.web.controller.item.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    Page<Item> findByMemberId(Pageable pageable, Long memberId);
}
