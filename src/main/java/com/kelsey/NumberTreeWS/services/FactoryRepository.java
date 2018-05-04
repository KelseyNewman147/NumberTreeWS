package com.kelsey.NumberTreeWS.services;

import com.kelsey.NumberTreeWS.models.Factory;
import org.springframework.data.repository.CrudRepository;


public interface FactoryRepository extends CrudRepository<Factory, Integer> {
    Factory findFactoryById(int id);
}