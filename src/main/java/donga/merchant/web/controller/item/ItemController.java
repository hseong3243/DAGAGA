package donga.merchant.web.controller.item;

import donga.merchant.domain.entity.Member;
import donga.merchant.domain.entity.item.ItemCode;
import donga.merchant.web.argumentresolver.Login;
import donga.merchant.web.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @ModelAttribute("itemCodes")
    public List<ItemCode> itemStatuses() {
        List<ItemCode> itemCodes = new ArrayList<>();
        itemCodes.add(new ItemCode("ON_SALE", "판매중"));
        itemCodes.add(new ItemCode("RESERVED", "거래중"));
        itemCodes.add(new ItemCode("OFF_SALE", "판매 완료"));
        return itemCodes;
    }

    @ModelAttribute("sortCodes")
    public List<SortCode> sortCodes() {
        List<SortCode> sortCodes = new ArrayList<>();
        sortCodes.add(new SortCode("price,ASC", "오름차순"));
        sortCodes.add(new SortCode("price,DESC", "내림차순"));
        return sortCodes;

    }

    @GetMapping
    public String items(@Login Member loginMember, Model model,
                        @RequestParam(required = false) String query,
                        @RequestParam(required = false) String status,
                        @RequestParam(required = false) String author,
                        @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ItemDto> itemDtos;

        model.addAttribute("query", query);
        model.addAttribute("status", status);
        model.addAttribute("author", author);

        if ((StringUtils.hasText(query) || StringUtils.hasText(status)) && !StringUtils.hasText(author)) {
            itemDtos = itemService.search(query, status, pageable);
            log.info("page={}", itemDtos.getTotalPages());
        } else if (StringUtils.hasText(author)) {
            itemDtos = itemService.searchDetail(query, author, status, pageable);
        } else {
            itemDtos = itemService.findAll(pageable);
        }

        model.addAttribute("items", itemDtos);
        model.addAttribute("page", itemDtos.getTotalPages());


        if (loginMember == null) {
            return "itemList";
        }

        model.addAttribute("member", loginMember);

        return "itemList";
    }


}
