package com.brokeragefirmchallenge.bfchallenge.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assets")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String assetName;

    private int size; // Total amount of this asset owned

    private double usableSize; // Amount available for transactions

    private String currency = "TRY"; // Currency field, fixed to "TRY"

}
