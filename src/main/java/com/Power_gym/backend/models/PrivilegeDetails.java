package com.Power_gym.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "privilege_details")
@IdClass(RolePrivilegeId.class)
public class PrivilegeDetails {

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Id
    @ManyToOne
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;

    @Column(name = "status")
    private int status;

}