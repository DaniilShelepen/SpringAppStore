package com.daniil.courses.services;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.Order;
import com.daniil.courses.repositories.AddressRepository;
import com.daniil.courses.repositories.UserRepository;
import com.daniil.courses.role_models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService {


    UserRepository userRepository;
    AddressRepository addressRepository;

    @Autowired
    public UserServiceIml(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public void removeAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public void addAddress(User user, Address address) {
        user.setAddresses(List.of(address));
        addressRepository.save(address);
    }

    @Override
    public Address refactorAddress(Address address) {

        Address add = addressRepository.findById(address.getId()).orElseThrow(RuntimeException::new);//TODO свою тут выкинь

        add.setCity(address.getCity());
        add.setStreet(address.getStreet());
        add.setBase(address.getBase());
        add.setFlat(address.getFlat());
        add.setFloor(address.getFloor());
        add.setEntrance(address.getEntrance());

        return addressRepository.save(add);
    }

    @Override
    public List<Item> getBasket() {
        return null;
    }

    @Override
    public void addItemToBasket(Integer count) {

    }

    @Override
    public void removeFromBasket(Integer id) {

    }

    @Override
    public void clearBasket() {

    }

    @Override
    public List<Item> viewAllItems() {
        return null;
    }

    @Override
    public void buyItems(Integer... id) {

    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }
}
