package com.bottle_app.dto;

import com.bottle_app.model.Bottle;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "Bottle_Res : 유리병 응답 DTO")
public class BottleResponseDto {
    @Schema(description = "유리병 id")
    private Long id;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "내용")
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @Schema(description = "작성한 날짜", example = "2024-06-20 17:30", type = "string")
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
