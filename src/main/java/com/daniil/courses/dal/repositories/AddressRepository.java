package com.daniil.courses.dal.repositories;


import com.daniil.courses.dal.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUserIdAndVisible(Integer userId,boolean visible);

    @Transactional
    Address findByUserIdAndIdAndVisible(Integer userId,Integer addressId,boolean visible);


}