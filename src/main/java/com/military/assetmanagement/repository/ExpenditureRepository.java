package com.military.assetmanagement.repository;
import com.military.assetmanagement.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    List<Expenditure> findByBaseId(Long baseId);
}
