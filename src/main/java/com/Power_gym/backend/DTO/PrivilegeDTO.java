package com.Power_gym.backend.DTO;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivilegeDTO {
    private Integer privilegeId;
    private String privilegeName;
    private String role;
    private int status;

    private String userCode;
    private List<Integer> privilegeIds;
}
