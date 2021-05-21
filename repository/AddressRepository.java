package com.example.app_jpa_relationships.repository;

import com.example.app_jpa_relationships.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
