package com.bottle_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "Page_Res : 유리병 페이지 DTO")
public class PageResponseDto {
    @Schema(description = "페이징된 유리병 목록")
    private List<BottleResponseDto> bottles;
    @Schema(description = "페이지 번호")
    private int pageNo;
    @Schema(description = "페이지 크기")
    private int pageSize;
    @Schema(description = "현재 로그인한 유저에게 도착한 총 유리병 개수")
    private long totalElements;
    @Schema(description = "총 페이지 개수")
    private int totalPages;
    @Schema(description = "마지막 페이지 여부")
    private boolean last;
}
