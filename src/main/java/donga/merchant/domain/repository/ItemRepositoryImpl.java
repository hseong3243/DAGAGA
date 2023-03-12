package donga.merchant.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import donga.merchant.domain.entity.item.Book;
import donga.merchant.domain.entity.item.QBook;
import donga.merchant.domain.entity.item.QItem;
import donga.merchant.web.controller.item.ItemDto;
import donga.merchant.web.controller.item.QItemDto;
import donga.merchant.web.querydsl.QueryDslUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static donga.merchant.domain.entity.QPost.*;
import static donga.merchant.domain.entity.item.QItem.*;

@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    QItem item = new QItem("item");
    QBook book = item.as(QBook.class);

    @Override
    public Page<ItemDto> searchV1(String condition, String status, Pageable pageable) {

        List<OrderSpecifier> ORDERS = getSorted(pageable);

        //Querydsl 은 아래와 같은 방법으로 슈퍼타입 서브타입 캐스팅을 해주면 된다.
        List<ItemDto> content = queryFactory
                .select(new QItemDto(
                        item.id,
                        item.name,
                        item.price,
                        book.author,
                        item.itemCode,
                        item.post.id
                ))
                .from(item)
                .leftJoin(item.post, post)
                .where(multiNameEq(condition)
                        .and(statusEq(status))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .fetch();

        long total = queryFactory
                .select(item.id)
                .from(item)
                .leftJoin(item.post, post)
                .where(multiNameEq(condition)
                        .and(statusEq(status)))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ItemDto> searchDetail(String condition, String author, String status, Pageable pageable) {
        List<OrderSpecifier> ORDERS = getSorted(pageable);
        List<ItemDto> content = queryFactory
                .select(new QItemDto(
                        item.id,
                        item.name,
                        item.price,
                        book.author,
                        item.itemCode,
                        item.post.id
                )).from(item)
                .leftJoin(item.post, post)
                .where(
                        multiNameEq(condition).and(statusEq(status))
                                .and(authorEq(author))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .fetch();

        long total = queryFactory
                .select(item.id)
                .from(item)
                .leftJoin(item.post, post)
                .where(multiNameEq(condition).and(statusEq(status))
                        .and(authorEq(author)))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression authorEq(String author) {
        return StringUtils.hasText(author) ? book.author.contains(author) : null;
    }

    private BooleanExpression statusEq(String status) {
        return StringUtils.hasText(status) ? item.itemCode.eq(status) : null;

    }

    private BooleanExpression nameEq(String condition) {
        return StringUtils.hasText(condition) ? item.name.contains(condition) : null;
    }

    private BooleanBuilder multiNameEq(String condition) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(condition)) {
            List<String> conditions = Arrays.asList(condition.split(" "));
            for (String s : conditions) {
                builder.and(item.name.contains(s));
            }
        }

        return builder;
    }

    private List<OrderSpecifier> getSorted(Pageable pageable) {
        List<OrderSpecifier> ORDERS = new ArrayList<>();

        if(!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                if ("price".equals(order.getProperty())) {
                    OrderSpecifier<?> itemPrice = QueryDslUtil.getSortedColumn(direction, item.price, "price");
                    ORDERS.add(itemPrice);
                    break;
                }
            }
        }
        return ORDERS;
    }

}
