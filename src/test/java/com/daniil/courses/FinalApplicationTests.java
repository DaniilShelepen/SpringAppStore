package com.daniil.courses;

import com.daniil.courses.models.*;
import com.daniil.courses.repositories.*;
import com.daniil.courses.role_models.User;
import com.daniil.courses.services.FilterService;
import com.daniil.courses.services.ManagerService;
import com.daniil.courses.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@EnableTransactionManagement
@SpringBootTest(classes = FinalApplication.class)
class FinalApplicationTests {

    private static final AppStore appStore = AppStore.builder()
            .build();

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    AppStoreRepository appStoreRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StoreItemRepository storeItemRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    ManagerService managerService;

    @Autowired
    FilterService filterService;

    private static final Item item1 = new Item(null, "MacOs", "good very mathafaka",
            "PC", "SSD 1024", "A93", new Date(1_565_568_000_000L));

    private static final Item item2 = new Item(null, "Iphone 11", "mb moy",
            "phone", "256GB", "A13", new Date(1_565_168_000_000L));
    private static final Item item3 = new Item(null, "AirPods", "birushi",
            "headPhones", "nun", "A3", new Date(1_563_568_000_000L));


    private static final User ME = User.builder()
            .userName("Daniil")
            .password("123")
            .phoneNumber("+37529234567")
            .build();

    private static final User NOT_ME = User.builder()
            .userName("Daniil12")
            .password("12345")
            .phoneNumber("+3752555567")
            .build();

    Address newAddress = Address.builder()
            .base("dada")
            .city("NP")
            .entrance("chto")
            .flat("15")
            .floor("4")
            .street("loxovskay")
            .user(ME)
            .build();

    Address not_newAddress = Address.builder()
            .base("da11da")
            .city("N11P")
            .entrance("c111hto")
            .flat("111115")
            .floor("41111")
            .street("lox1111ovskay")
            .user(NOT_ME)
            .build();


    @Test
    void contextLoads() {
//айтемы юсер адрес

        Iterable<Item> itemsStore = itemRepository.saveAll(
                List.of(
                        item1,
                        item2,
                        item3
                )
        );

        List<StoreItem> storeItems = StreamSupport.stream(itemsStore.spliterator(), false)
                .map(item -> StoreItem.builder()
                        .appStore(appStore)
                        .item(Item.builder().id(item.getId()).build())
                        .price(BigDecimal.valueOf(Math.random() * 1500 + 500))
                        .available(true)
                        .build())
                .collect(Collectors.toList());
        appStore.setItems(storeItems);

        appStoreRepository.save(appStore);

        userRepository.save(ME);

        userRepository.save(NOT_ME);

        userService.addAddressByUser(ME, newAddress);
        userService.addAddressByUser(NOT_ME, not_newAddress);


        // log.info("{}", userService.getAllAddressesByUser(ME.getId()));


        userService.addItemToBasketByUser(storeItems.get(2), ME, 5);
        userService.addItemToBasketByUser(storeItems.get(0), ME, 4);
        userService.addItemToBasketByUser(storeItems.get(1), ME, 3);
        userService.addItemToBasketByUser(storeItems.get(2), NOT_ME, 5);

        orderRepository.save(Order.builder()
                .status("DONE")
                .address(newAddress)
                .user(ME)
                .date(new Date(1_565_568_000_032L))
                .dateOfRefactoring(new Date(1_565_568_000_084L))
                .build());
        log.info("{}", userService.getAllOrdersByUser(ME));
        //   log.info(String.valueOf(userService.getUserBasket(ME)));
        // userService.removeAddress(1);

        // userService.removeFromBasket(storeItems.get(2), ME);
        userService.clearBasketByUser(ME);

        managerService.addNewItem("item","norm","da","nu norm takoe",
                "cpu da",new Date(1_213_3333_2345L),BigDecimal.valueOf(1242.77),true);

    }

    @Test
    void addAddress() {


    }

}
