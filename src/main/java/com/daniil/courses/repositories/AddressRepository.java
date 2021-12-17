package com.daniil.courses.repositories;


import com.daniil.courses.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    void deleteById(Integer id);

    @Query(value = "SELECT id from Address where id = ?1")
    List<Address> getAddressByUserId(Integer userId);
}