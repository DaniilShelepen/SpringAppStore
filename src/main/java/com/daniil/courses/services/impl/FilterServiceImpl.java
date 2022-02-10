package com.daniil.courses.services.impl;

import com.daniil.courses.dal.entity.Item;
import com.daniil.courses.dal.entity.Item_;
import com.daniil.courses.dal.entity.Order;
import com.daniil.courses.dal.entity.StoreItem;
import com.daniil.courses.dal.repositories.OrderRepository;
import com.daniil.courses.dal.repositories.StoreItemRepository;
import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.mappers.OrderConvertor;
import com.daniil.courses.mappers.StoreItemConvertor;
import com.daniil.courses.services.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {

    private final StoreItemRepository storeItemRepository;
    private final OrderRepository orderRepository;
    private final StoreItemConvertor storeItemConvertor;
    private final OrderConvertor orderConvertor;
    private final EntityManager entityManager;

    @Override
    public List<UserStoreItemDto> filter(String name, String description, String type,
                                         String driverConfiguration, String CPU, LocalDate releaseDate, BigDecimal price) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);

        Root<Item> root = cq.from(Item.class);

        Predicate itemName = cb.like(cb.lower(root.get(Item_.NAME)), ("%" + name + "%").toLowerCase());
        Predicate itemDescription = cb.like(cb.lower(root.get(Item_.DESCRIPTION)), ("%" + description + "%").toLowerCase());
        Predicate itemType = cb.like(cb.lower(root.get(Item_.TYPE)), ("%" + type + "%").toLowerCase());
        Predicate itemDriverConfiguration = cb.like(cb.lower(root.get(Item_.DRIVER_CONFIGURATION)), ("%" + driverConfiguration + "%").toLowerCase());
        Predicate itemCPU = cb.like(cb.lower(root.get(Item_.C_PU)), ("%" + CPU + "%").toLowerCase());
        Predicate itemReleaseDate = cb.greaterThanOrEqualTo(root.get(Item_.RELEASE_DATE), releaseDate);


        cq.where(itemName, itemDescription, itemType, itemDriverConfiguration, itemCPU, itemReleaseDate);

        TypedQuery<Item> query = entityManager.createQuery(cq);


        List<Item> itemList = query.getResultList();

        List<StoreItem> finalLIst = new ArrayList<>();

        for (Item item : itemList
        ) {
            StoreItem storeItem = storeItemRepository.findByItemId(item.getId());
            if (price == null)
                finalLIst.add(storeItem);
            else if (storeItem.getPrice().compareTo(price) <= 0)
                finalLIst.add(storeItem);

        }

        return finalLIst.stream().map(storeItemConvertor::convertForUser).collect(Collectors.toList());

    }

    @Override
    public List<ManagerOrderDto> filterUserOrderByStatus(Integer userId, String orderStatuses) {

        return orderRepository.findAllByUserId(userId).stream()
                .filter(order -> order.getStatus().toString().contains(orderStatuses.toLowerCase()))
                .map(orderConvertor::convertForManager)
                .collect(Collectors.toList());
    }


    @Override
    public List<ManagerOrderDto> filterUserOrderByDateNew(Integer userId) {

        List<ManagerOrderDto> finalList =
                orderRepository.findAllByUserId(userId).stream()
                        .sorted(Comparator.comparing(Order::getDate))
                        .map(orderConvertor::convertForManager)
                        .collect(Collectors.toList());
        Collections.reverse(finalList);
        return finalList;

    }

    @Override
    public List<ManagerOrderDto> filterUserOrderByDateOld(Integer userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .sorted(Comparator.comparing(Order::getDate))
                .map(orderConvertor::convertForManager)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getCheap() {
        return storeItemRepository.findAllByAvailable(true).stream()
                .sorted(Comparator.comparing(StoreItem::getPrice))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getExpensive() {
        List<UserStoreItemDto> list = storeItemRepository.findAllByAvailable(true).stream()
                .sorted(Comparator.comparing(StoreItem::getPrice))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());

        Collections.reverse(list);

        return list;
    }

}
