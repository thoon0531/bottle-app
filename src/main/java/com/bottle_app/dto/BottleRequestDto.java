package com.bottle_app.dto;

import com.bottle_app.model.Bottle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(title = "Bottle_Req : 유리병 작성 요청 DTO")
public class BottleRequestDto {
    @Schema(description = "제목")
    private String title;
    @Schema(description = "내용")
    private String content;

    public Bottle toEntity(){
        return Bottle.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
