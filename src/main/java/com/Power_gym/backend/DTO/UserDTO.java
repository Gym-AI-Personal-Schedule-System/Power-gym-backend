package com.Power_gym.backend.DTO;


import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    private String userCode;
    private String username;
    private String email;
    private String password;
    private Integer rolesId;
    private String name;
    private String mobileNum;
    private String address;
    private String gender;
    private Integer height;
    private Integer weight;
    private Double bmi;
    private Integer age =0;
    private Date createDateTime;
    private Date updateDateTime;
    private int isActive;

}
