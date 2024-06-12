package com.bottle_app.dto;

import com.bottle_app.model.Bottle;
import lombok.Data;

import java.util.Date;

@Data
public class BottleRequestDto {
    private String title;
    private String content;

    public Bottle toEntity(){
        return Bottle.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
