package com.military.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "transfer_items",
        schema = "military_asset_management"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "transfer_id",
            nullable = false
    )
    private Transfer transfer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "equipment_id",
            nullable = false
    )
    private Equipment equipment;

    @Column(nullable = false)
    private Integer quantity;
}