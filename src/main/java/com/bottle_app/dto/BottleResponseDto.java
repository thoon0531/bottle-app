package com.bottle_app.dto;

import com.bottle_app.model.Bottle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BottleResponseDto {
    private Long id;
    private String title;
    private String content;
    private Date createdAt;

    public static BottleResponseDto entityToDto(Bottle bottle) {
        return BottleResponseDto.builder()
                .id(bottle.getId())
                .title(bottle.getTitle())
                .content(bottle.getContent())
                .createdAt(bottle.getCreatedAt())
                .build();
    }
}
