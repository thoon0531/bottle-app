package com.bottle_app.dto;

import com.bottle_app.model.Bottle;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
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
