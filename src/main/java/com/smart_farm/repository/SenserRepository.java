package com.smart_farm.repository;

import com.smart_farm.entity.Senser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SenserRepository extends JpaRepository<Senser, Long> {
    Senser findTopByOrderByIdDesc();
    List<Senser> findAllByOrderByIdDesc();
}
