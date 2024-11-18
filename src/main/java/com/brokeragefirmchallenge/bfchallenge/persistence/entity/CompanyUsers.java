package com.brokeragefirmchallenge.bfchallenge.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "companyusers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String role;  // Role field to store the role of the user

}
