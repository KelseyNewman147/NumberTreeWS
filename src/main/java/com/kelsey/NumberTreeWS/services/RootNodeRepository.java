package com.kelsey.NumberTreeWS.services;

import com.kelsey.NumberTreeWS.models.RootNode;
import org.springframework.data.repository.CrudRepository;

public interface RootNodeRepository extends CrudRepository<RootNode, Integer> {
    RootNode findById(int id);
}
