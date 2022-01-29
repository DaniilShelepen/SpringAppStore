package com.daniil.courses.services.impl;

import com.daniil.courses.dto.BasketDto;
import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.exceptions.BasketIsEmpty;
import com.daniil.courses.exceptions.StoreItemIsNotFound;
import com.daniil.courses.exceptions.UserNotFound;
import com.daniil.courses.models.Basket;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.*;
import com.daniil.courses.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {


    UserRepository userRepository;
    AddressRepository addressRepository;
    StoreItemRepository storeItemRepository;
    BasketRepository basketRepository;
    OrderRepository orderRepository;

    @Autowired
    public BasketServiceImpl(UserRepository userRepository, AddressRepository addressRepository,
                           StoreItemRepository storeItemRepository, BasketRepository basketRepository,
                           OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.storeItemRepository = storeItemRepository;
        this.basketRepository = basketRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<BasketDto> getBasketByUser(Integer userId) {
        return basketRepository.findBasketByUserId(userId).stream()
                .filter(basket -> basket.getStoreItem().isAvailable())
                .map(basket -> new BasketDto(ItemDto.toItemDto(basket.getStoreItem().getItem())
                        , basket.getCount(), basket.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public void addItemToBasketByUser(Integer storeItemId, Integer userId, Integer count) {

        StoreItem storeItem = storeItemRepository.findByIdAndAvailable(storeItemId, true);

        if (storeItem == null)
            throw new StoreItemIsNotFound("Store item is not found");

        Basket basket = basketRepository.findByStoreItemIdAndUserId(storeItemId, userId);

        if (basket != null) {
            basket.setCount(basket.getCount() + count);
            basketRepository.save(basket);
        } else {
            basketRepository.save(Basket.builder()
                    .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User not found")))
                    .storeItem(storeItem)
                    .count(count)
                    .price(storeItem.getPrice().multiply(BigDecimal.valueOf(count)))
                    .build()
            );
        }
    }

    @Override
    public void removeFromBasketByUser(Integer storeItemId, Integer userId) {
        basketRepository.findAllByUserId(userId).stream().findAny().orElseThrow(() -> new BasketIsEmpty("Your basket is empty"));
        basketRepository.findAllByUserId(userId).stream().filter(basket -> basket.getStoreItem().getId()
                .equals(storeItemId)).findFirst().orElseThrow(() -> new BasketIsEmpty("You don`t have such item"));
        basketRepository.deleteByStoreItemIdAndUserId(storeItemId, userId);
    }

    @Override
    public void clearBasketByUser(Integer userId) {
        basketRepository.findAllByUserId(userId).stream().findAny().orElseThrow(() -> new BasketIsEmpty("Your basket is empty"));
        basketRepository.deleteAllByUserId(userId);
    }


}
