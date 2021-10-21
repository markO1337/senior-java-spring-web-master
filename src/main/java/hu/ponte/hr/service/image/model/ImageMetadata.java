package hu.ponte.hr.service.image.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageMetadata {

    private long id;
    private String name;
    private String mimeType;
    private long size;
    private String digitalSign;

}
