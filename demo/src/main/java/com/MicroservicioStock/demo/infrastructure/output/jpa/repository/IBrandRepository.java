package com.MicroservicioStock.demo.infrastructure.output.jpa.repository;

import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity,Long>  {
    Optional<BrandEntity> findByName(String aString);

}
