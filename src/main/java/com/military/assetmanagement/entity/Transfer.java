package com.military.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "transfers",
        schema = "military_asset_management"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "from_base_id",
            nullable = false
    )
    private Base fromBase;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "to_base_id",
            nullable = false
    )
    private Base toBase;

    @Column(
            name = "transfer_date",
            nullable = false
    )
    private LocalDateTime transferDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferStatus status;

    @Column(name = "reference_number")
    private String referenceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(
            mappedBy = "transfer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TransferItem> items = new ArrayList<>();

    // Helper method
    public void addItem(TransferItem item) {
        items.add(item);
        item.setTransfer(this);
    }

    // Helper method
    public void removeItem(TransferItem item) {
        items.remove(item);
        item.setTransfer(null);
    }

}
