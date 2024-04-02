package com.mobi.mobe.dto;

import com.mobi.mobe.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private Long id;
    private Long userId;
}
