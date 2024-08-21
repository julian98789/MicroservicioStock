package com.MicroservicioStock.demo.infrastructure.output.jpa.repository;

import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.CategoriEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoriRepository extends JpaRepository<CategoriEntity,Long> {

    Optional<CategoriEntity> findByName(String aString);
    Page<CategoriEntity> findAll(Pageable pageable);

}
