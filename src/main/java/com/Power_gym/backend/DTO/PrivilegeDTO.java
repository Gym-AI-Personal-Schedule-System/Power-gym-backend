package com.Power_gym.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrivilegeDTO {
    private Integer privilegeId;
    private String privilegeName;
    private int status;

    private String userCode;
    private List<Integer> privilegeIds;
}
