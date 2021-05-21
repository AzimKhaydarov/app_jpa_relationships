package com.example.app_jpa_relationships.repository;

import com.example.app_jpa_relationships.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);

}
