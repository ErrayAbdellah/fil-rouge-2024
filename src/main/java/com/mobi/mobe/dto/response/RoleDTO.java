package com.mobi.mobe.dto.response;

import com.mobi.mobe.entities.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private String name ;
    private List<Authority> authorities;
}
