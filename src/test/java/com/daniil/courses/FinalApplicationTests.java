package com.daniil.courses;

import com.daniil.courses.models.AppStore;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.AppStoreRepository;
import com.daniil.courses.repositories.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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



    private static final Item item1 = new Item(null, "MacOs", "good very mathafaka",
            "PC", "SSD 1024", "A93", true, new Date(1_565_568_000_000L));


    Iterable<Item> itemsStore = itemRepository.saveAll(
            List.of(
                    item1
            )
    );


    @Test
    void contextLoads() {


        List<StoreItem> storeItems = StreamSupport.stream(itemsStore.spliterator(), false)
                .map(item -> StoreItem.builder()
                        .appStore(appStore)
                        .item(Item.builder().id(item.getId()).build())
                        .price(BigDecimal.valueOf(Math.random() * 1500 + 500))
                        .build())
                .collect(Collectors.toList());
        appStore.setItems(storeItems);

        appStoreRepository.save(appStore);

    }

}
