package com.military.assetmanagement.repository;
import com.military.assetmanagement.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("""
    SELECT DISTINCT t
    FROM Transfer t
    LEFT JOIN FETCH t.fromBase
    LEFT JOIN FETCH t.toBase
    LEFT JOIN FETCH t.items
    """)
    List<Transfer> findAllWithDetails();

    @Query("""
    SELECT DISTINCT t
    FROM Transfer t
    LEFT JOIN FETCH t.fromBase
    LEFT JOIN FETCH t.toBase
    LEFT JOIN FETCH t.items
    WHERE t.fromBase.id = :baseId
       OR t.toBase.id = :baseId
   """)
    List<Transfer> findHistoryByBaseId(@Param("baseId") Long baseId);
    List<Transfer> findByFromBaseIdOrToBaseId(Long fromBaseId, Long toBaseId);
}
