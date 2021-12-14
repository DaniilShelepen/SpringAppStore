package com.daniil.courses;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.AppStore;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.AddressRepository;
import com.daniil.courses.repositories.AppStoreRepository;
import com.daniil.courses.repositories.ItemRepository;
import com.daniil.courses.repositories.UserRepository;
import com.daniil.courses.role_models.User;
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
    UserService userService;


    private static final Item item1 = new Item(null, "MacOs", "good very mathafaka",
            "PC", "SSD 1024", "A93", true, new Date(1_565_568_000_000L));

    private static final Item item2 = new Item(null, "Iphone 11", "mb moy",
            "phone", "256GB", "A13", true, new Date(1_565_168_000_000L));
    private static final Item item3 = new Item(null, "AirPods", "birushi",
            "headPhones", "nun", "A3", true, new Date(1_563_568_000_000L));


    private static final User ME = User.builder()
            .userName("Daniil")
            .password("123")
            .phoneNumber("+37529234567")
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


    @Test
    void contextLoads() {
//айтемы юсер адрес
        {
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
                        .build())
                .collect(Collectors.toList());
        appStore.setItems(storeItems);

        appStoreRepository.save(appStore);

        userRepository.save(ME);

        userService.addAddress(ME, newAddress);
    }

        newAddress.setFloor("99");
        newAddress.setCity("PiPiPi");


        userService.refactorAddress(newAddress);


       // userService.removeAddress(1);

    }

    @Test
    void addAddress() {


    }

}
