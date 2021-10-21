package hu.ponte.hr.controller.image.dto;

import lombok.*;

@Data
public class ImageMetadataResponseDto {

    private String id;
    private String name;
    private String mimeType;
    private long size;
    private String digitalSign;

}
