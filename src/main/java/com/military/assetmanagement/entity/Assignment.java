package com.military.assetmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments",schema ="military_asset_management")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Base base;

    @ManyToOne(optional = false)
    private Equipment equipment;

    @Column(nullable = false)
    private String personnelName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    @ManyToOne
    private User assignedBy;
}
