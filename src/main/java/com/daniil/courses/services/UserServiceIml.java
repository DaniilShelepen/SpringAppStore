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
import java.util.stream.Collectors;

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
    public List<Address> getAllUserAddresses(User user) {
        return addressRepository.findAll().stream()
                .filter(address1 -> address1.getUser().getId()
                        .equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public List<Address> addAddress(User user, Address address) {
        user.setAddresses(List.of(address));
        addressRepository.save(address);
        return addressRepository.findAll().stream()
                .filter(address1 -> address1.getUser().getId()
                        .equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Address refactorAddress(Address address) {

        Address changedAddress = addressRepository.findById(address.getId()).orElseThrow(RuntimeException::new);//TODO свою тут выкинь

        changedAddress.setCity(address.getCity());
        changedAddress.setStreet(address.getStreet());
        changedAddress.setBase(address.getBase());
        changedAddress.setFlat(address.getFlat());
        changedAddress.setFloor(address.getFloor());
        changedAddress.setEntrance(address.getEntrance());

        return addressRepository.save(changedAddress);
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
