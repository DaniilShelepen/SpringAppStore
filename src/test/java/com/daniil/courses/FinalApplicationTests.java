package com.daniil.courses;

import com.daniil.courses.dal.entity.*;
import com.daniil.courses.dal.repositories.*;
import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.services.FilterService;
import com.daniil.courses.services.ManagerService;
import com.daniil.courses.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Slf4j
@EnableTransactionManagement
@SpringBootTest(classes = FinalApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
class FinalApplicationTests {


    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ManagerRepository managerRepository;
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
            "PC", "SSD 1024", "A93", LocalDate.of(2002, 3, 15),true);

    private static final Item item2 = new Item(null, "Iphone 11", "mb moy",
            "phone", "256GB", "A13", LocalDate.of(2002, 3, 15),true);
    private static final Item item3 = new Item(null, "AirPods", "birushi",
            "headPhones", "nun", "A3", LocalDate.of(2002, 3, 15),true);


    private static final User ME = User.builder()
            .name("Daniil")
            .surname("aaa212aa")
            .birthday(LocalDate.of(2002, 3, 15))
            .password("123")
            .phoneNumber("+37529234567")
            .build();

    private static final User NOT_ME = User.builder()
            .name("Daniil12")
            .surname("aaaaa")
            .birthday(LocalDate.of(2002, 3, 15))
            .password("12345")
            .phoneNumber("+3752555567")
            .build();

    Address newAddress = Address.builder()
            .base("dada")
            .city("NP")
            .entrance("chto")
            .flat("15")
            .floor("notnotnotnotbot")
            .street("loxovskay")
            .user(ME)
            .visible(true)
            .build();

    Address newAddress2 = Address.builder()
            .base("dad222a")
            .city("NP2")
            .entrance("chto")
            .flat("1522")
            .floor("4")
            .street("lo222xovskay")
            .user(ME)
            .visible(true)
            .build();

    Address not_newAddress = Address.builder()
            .base("da11da")
            .city("N11P")
            .entrance("c111hto")
            .flat("111115")
            .floor("41111")
            .street("lox1111ovskay")
            .user(NOT_ME)
            .visible(true)
            .build();

    Address not_newAddress1 = Address.builder()
            .base("da11da")
            .city("N11P")
            .entrance("c111hto1")
            .flat("111115")
            .floor("41111")
            .street("lox1111ovskay")
            .user(NOT_ME)
            .visible(true)
            .build();

    Order order = Order.builder()
            .address(newAddress)
            .user(ME)
            .date(LocalDate.of(2002, 3, 15))
            .dateOfRefactoring(new Date())
           // .status("AAAA")
            .build();

    Order order1 = Order.builder()
            .address(newAddress)
            .user(ME)
            .date(LocalDate.of(2002, 3, 15))
            .dateOfRefactoring(new Date())
            //.status("AAAAAAa")
            .storeItem(null)
            .build();

    Order order2 = Order.builder()
            .address(newAddress)
            .user(ME)
            .date(LocalDate.of(2002, 3, 15))
            .dateOfRefactoring(new Date())
           // .status("AAAA")
            .build();

    Manager manager = Manager.builder()
            .userName("da")
            .password("net")
            .build();


    ItemDto itemDto = ItemDto.builder()
            .name("aasd")
            .CPU("sdsad")
            .description("sdsa")
            .driverConfiguration("dsa")
            .releaseDate(LocalDate.of(2002, 3, 15))
            .type("sdsda")
            .build();
    ItemDto itemDto2 = ItemDto.builder()
            .name("2")
            .CPU("s")
            .description("sdsa")
            .driverConfiguration("dsa")
            .releaseDate(LocalDate.of(2002, 3, 15))
            .type("sd")
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
                        .item(Item.builder().id(item.getId()).build())
                        .price(BigDecimal.valueOf(Math.random() * 1500 + 500))
                        .available(true)
                        .build())
                .collect(Collectors.toList());


        storeItemRepository.saveAll(storeItems);
        userRepository.save(ME);
        userRepository.save(NOT_ME);


    }


    @Test
    void start() {


        managerRepository.save(manager);


        RestTemplate restTemplate = new RestTemplate();

        /** типо покупаем **/

        HttpHeaders headers3 = new HttpHeaders();
        headers3.setContentType(MediaType.APPLICATION_JSON);
        headers3.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers3.set("api-secret", "73639230209630325687467956934678461280299897415334906606780485480290810227978544877781597859779756768343265126280119675915116");

        String url3 = "http://localhost:7070/external-api/acquires/7edfc44d-f7d5-411a-8516-33f27d039d86/payments";
        String requestJson3 = "{\n" +
                "    \"accountId\": \"270ec18e-995f-4e4e-a5d3-7eee647f79f7\",\n" +
                "    \"amount\": {\n" +
                "        \"currency\": \"USD\",\n" +
                "        \"value\": 150.14\n" +
                "    },\n" +
                "    \"externalId\": \"xa3aa3449-925e-4dd0-9786-a726067896263\",\n" +
                "    \"purpose\": \"labore ut veniam sunt commodo\",\n" +
                "    \"acquireWebHook\": \"http://CUcff.chhVKLaxPnKbT\"\n" +
                "}";


        HttpEntity<String> entity3 = new HttpEntity<>(requestJson3, headers3);
        log.warn("{}", restTemplate.postForObject(url3, entity3, String.class));

    }


    @Test
    void admin() {
    managerService.setAvailable(1,false,1);
    }

}
