package com.Power_gym.backend.models;

import com.Power_gym.backend.models.enums.ERole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20,name = "name")
    private ERole name;

    @Column(name = "is_active",columnDefinition = "integer default 1")
    private int isActive ;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                ", isActive=" + isActive +
                '}';
    }
}