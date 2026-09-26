package com.military.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchases",schema ="military_asset_management")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Purchase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Base base;

    @ManyToOne(optional = false)
    private Equipment equipment;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    private String supplier;
    private String referenceNumber;

    @ManyToOne
    private User createdBy;
}
