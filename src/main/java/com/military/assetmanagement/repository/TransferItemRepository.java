package com.military.assetmanagement.repository;

import com.military.assetmanagement.entity.TransferItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferItemRepository extends JpaRepository<TransferItem, Long> {
    //List<TransferItem> findByFromBaseIdOrToBaseId(Long fromBaseId, Long toBaseId);
}
