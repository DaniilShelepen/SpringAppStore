package com.daniil.courses.services.impl;

import com.daniil.courses.dal.repositories.BasketRepository;
import com.daniil.courses.dal.repositories.StoreItemRepository;
import com.daniil.courses.dal.repositories.UserRepository;
import com.daniil.courses.dto.BasketDto;
import com.daniil.courses.exceptions.BasketIsEmpty;
import com.daniil.courses.exceptions.StoreItemIsNotFound;
import com.daniil.courses.exceptions.UserNotFound;
import com.daniil.courses.mappers.BasketConvertor;
import com.daniil.courses.dal.entity.Basket;
import com.daniil.courses.dal.entity.StoreItem;
import com.daniil.courses.services.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {


    private final UserRepository userRepository;
    private final StoreItemRepository storeItemRepository;
    private final BasketRepository basketRepository;
    private final BasketConvertor basketConvertor;


    @Override
    public List<BasketDto> getBasketByUser(Integer userId) {
        return basketRepository.findBasketByUserId(userId).stream()
                .filter(basket -> basket.getStoreItem().isAvailable())
                .map(basketConvertor::convert)
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
