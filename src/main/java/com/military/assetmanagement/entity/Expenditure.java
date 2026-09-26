package com.military.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenditures",schema ="military_asset_management")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Expenditure {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Base base;

    @ManyToOne(optional = false)
    private Equipment equipment;

    @Column(nullable = false)
    private Integer quantity;

    private String reason;

    @Column(nullable = false)
    private LocalDateTime expendedAt;

    @ManyToOne
    private User recordedBy;
}
