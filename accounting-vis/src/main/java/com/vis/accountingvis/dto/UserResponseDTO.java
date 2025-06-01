package com.vis.accountingvis.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    private Integer uid;
    private String username;

    public UserResponseDTO(Integer uid, String username) {
        this.uid = uid;
        this.username = username;
    }
}