package com.kelsey.NumberTreeWS.services;

import com.kelsey.NumberTreeWS.models.ChildNode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChildNodeRepository extends CrudRepository<ChildNode, Integer> {
    List<ChildNode> findChildByFactoryId(int Id);
}
