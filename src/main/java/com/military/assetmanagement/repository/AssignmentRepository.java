package com.military.assetmanagement.repository;
import com.military.assetmanagement.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByBaseId(Long baseId);
}
