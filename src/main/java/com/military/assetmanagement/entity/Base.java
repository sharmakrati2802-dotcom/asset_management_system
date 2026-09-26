package com.military.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bases",schema ="military_asset_management")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Base {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    private String location;
}
