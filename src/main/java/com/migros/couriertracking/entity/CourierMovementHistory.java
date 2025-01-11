package com.migros.couriertracking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "couirerMovementHistory")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourierMovementHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private Long storeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private Courier courier;

}
