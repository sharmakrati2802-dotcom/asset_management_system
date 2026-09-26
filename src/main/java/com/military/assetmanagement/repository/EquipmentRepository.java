package com.military.assetmanagement.repository;
import com.military.assetmanagement.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {}
