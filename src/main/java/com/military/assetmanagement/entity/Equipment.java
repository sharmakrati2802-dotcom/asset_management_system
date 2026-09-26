package com.military.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipment",schema ="military_asset_management")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Equipment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType equipmentType;

    private String description;
    private String unit;
}
